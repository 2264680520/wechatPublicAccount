package com.zhoumin.wechat.dao;

import com.zhoumin.wechat.message.BaseMessage;
import com.zhoumin.wechat.message.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Service;


public interface RecordDao extends JpaRepository<Record, Long>{
}
