package com.zhoumin.wechat.service.impl;


import com.zhoumin.wechat.dao.RecordDao;
import com.zhoumin.wechat.dao.SuperAdminDao;
import com.zhoumin.wechat.dao.VerificationDao;
import com.zhoumin.wechat.dto.SuperAdmin;
import com.zhoumin.wechat.dto.Verification;
import com.zhoumin.wechat.message.*;
import com.zhoumin.wechat.service.MessageService;
import com.zhoumin.wechat.utils.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ronghe
 * @create 2020-02-17 15:33
 */
@Slf4j
@Service
public class MessageServiceImpl implements MessageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);
    int authCodeNew = 0;
    @Autowired
    private RecordDao recordDao;
    @Autowired
    private SuperAdminDao superAdminDao;
    @Autowired
    private VerificationDao verificationDao;

    @Override
    public String newMessageRequest(HttpServletRequest request) {
        String respMessage = null;
        try {
            // xml请求解析
            Map<String, String> requestMap = MessageUtil.xmlToMap(request);
            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
            // 消息内容
            String content = requestMap.get("Content");
            LOGGER.info("FromUserName is:" + fromUserName + ", ToUserName is:" + toUserName + ", MsgType is:" + msgType + "content is:" + content);
            Record record = new Record();
            record.setToUserName(toUserName);
            record.setFromUserName(fromUserName);
            record.setContent(content);
            record.setMsgType(msgType);
            recordDao.save(record);
            // 文本消息
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                respMessage = sendText(requestMap);
                LOGGER.info("xml" + respMessage);
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                respMessage = sendImage(requestMap);
            }
            // 事件推送
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                String eventType = requestMap.get("Event");
                // 订阅
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    // 将图文消息对象转换成xml字符串
                    respMessage = sendNewsMessage(requestMap);

                }
                // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {}
                // 自定义菜单点击事件
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    // 事件KEY值，与创建自定义菜单时指定的KEY值对应
                    String eventKey = requestMap.get("EventKey");
                    if (eventKey.equals("return_content")) {
                        TextMessage text = new TextMessage();
                        text.setContent("赞赞赞");
                        text.setToUserName(fromUserName);
                        text.setFromUserName(toUserName);
                        text.setCreateTime(new Date().getTime());
                        text.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                        respMessage = MessageUtil.textMessageToXml(text);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("error......" + e);
        }
        return respMessage;
    }

    /**
     * 发送文本信息
     *
     * @param requestMap
     * @return
     */
    public String sendText(Map<String, String> requestMap) {
        TextMessage text = new TextMessage();
        //发送方变成接收方
        text.setToUserName(requestMap.get("FromUserName"));
        text.setFromUserName(requestMap.get("ToUserName"));
        text.setCreateTime(new Date().getTime());
        text.setMsgType(requestMap.get("MsgType"));
        //这里根据关键字执行相应的逻辑，只有你想不到的，没有做不到的
        if ("登录".equals(requestMap.get("Content"))) {
            text = verify(text);
        } else if (requestMap.get("Content").contains("绑定+@")) {
            //LOGGER.info("正在进行绑定！");
            String binding = binding(requestMap);
            text.setContent(binding);
        } else {
            //不做任何响应
            return "success";
        }
        return MessageUtil.textMessageToXml(text);
    }

    /**
     * 微信发送给用户图片信息
     *
     * @param requestMap
     * @return
     */
    public String sendImage(Map<String, String> requestMap) {
        LOGGER.info("图片发送");
        String mediaId = requestMap.get("MediaId");
        LOGGER.info("图片MediaId:" + mediaId);
        ImageMessage imageMessage = new ImageMessage();
        imageMessage.setToUserName(requestMap.get("FromUserName"));
        imageMessage.setFromUserName(requestMap.get("ToUserName"));
        imageMessage.setCreateTime(new Date().getTime());
        imageMessage.setMsgType(requestMap.get("MsgType"));
        Image image = new Image();
        image.setMediaId(mediaId);
        imageMessage.setImage(image);
        return MessageUtil.imageMessageToXml(imageMessage);
    }

    /**
     * 发送图文消息
     *
     * @param requestMap
     * @return
     */
    public String sendNewsMessage(Map<String, String> requestMap) {
        //对图文消息
        NewsMessage newmsg = new NewsMessage();
        newmsg.setToUserName(requestMap.get("FromUserName"));
        newmsg.setFromUserName(requestMap.get("ToUserName"));
        newmsg.setCreateTime(new Date().getTime());
        newmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        newmsg.setFuncFlag(0);
        List<Article> articleList = new ArrayList<>();

        Article article = new Article();
        article.setTitle("我是一个图文");
        article.setDescription("我是描述信息");
        article.setPicUrl("https://sfault-avatar.b0.upaiyun.com/110/007/1100070317-5abcb09d42224_huge256");
        article.setUrl("https://www.jianshu.com/");
        articleList.add(article);
        // 设置图文消息个数
        newmsg.setArticleCount(articleList.size());
        // 设置图文消息包含的图文集合
        newmsg.setArticles(articleList);
        return MessageUtil.newsMessageToXml(newmsg);
    }

    /**
     * 登陆验证码
     *
     * @param textMessage
     * @return
     */
    public TextMessage verify(TextMessage textMessage) {
        log.info("fromUserName:" + textMessage.getFromUserName());
        log.info("toUserName:" + textMessage.getToUserName());

        SuperAdmin byFromUserName = superAdminDao.findByWeChatNumber(textMessage.getToUserName());
        log.info("获取该用户：" + byFromUserName);
        if (byFromUserName == null) {
            textMessage.setContent("对不起！您还未绑定账号，绑定账号格式->绑定+@账号名");
            return textMessage;
        }
        authCodeNew = (int) Math.round(Math.random() * (9999 - 1000) + 1000);
        String s = String.valueOf(authCodeNew);
        Verification verification = new Verification();
        verification.setCode(s);
        //设置要发送方的微信号
        verification.setWeChatNumber(textMessage.getToUserName());
        //保存验证码
        verificationDao.save(verification);
        //自动回复
        textMessage.setContent(s);
        return textMessage;
    }

    /**
     * 用户名和微信号进行绑定
     *
     * @param requestMap
     */
    public String binding(Map<String, String> requestMap) {
        String content = requestMap.get("Content");
        String substring = content.substring(4);
        SuperAdmin byUserName = superAdminDao.findByUserName(substring);
        String fromUserName = requestMap.get("FromUserName");
        //判断是否有该账号
        if (byUserName == null) {
            LOGGER.info("对不起该账号不存在");
            return "对不起该账号不存在";
        }
        if (byUserName.getWeChatNumber() != null) {
            LOGGER.info("该账号已绑定");
            return "该账号已绑定";
        }
        byUserName.setWeChatNumber(fromUserName);
        LOGGER.info("绑定成功！" + byUserName);
        superAdminDao.saveAndFlush(byUserName);
        return "绑定成功！";
    }
}
