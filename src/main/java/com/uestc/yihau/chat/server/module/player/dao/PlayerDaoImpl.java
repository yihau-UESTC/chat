package com.uestc.yihau.chat.server.module.player.dao;


import com.uestc.yihau.chat.server.module.player.dao.entity.Player;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PlayerDaoImpl implements PlayerDao{

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public Player getPlayerById(long playerId) {
        SqlSession session = sqlSessionFactory.openSession();
        Player player = session.selectOne("player.getPlayerById", playerId);
        return player;
    }

    @Override
    public Player getPlayerByName(String playerName) {
        SqlSession session = sqlSessionFactory.openSession();
        Player player = session.selectOne("player.getPlayerByName", playerName);
        return player;
    }

    @Override
    public Player insertPlayer(Player player) {
        SqlSession session = sqlSessionFactory.openSession();
        Player player1 = session.selectOne("player.insertPlayer", player);
        return player1;
    }
}

