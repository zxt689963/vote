package com.ok8.front.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.ok8.common.db.DBManipulation;
import com.ok8.common.db.JdbcBaseDao;

public class VoteDao extends JdbcBaseDao {
	
	
	public VoteDao(DBManipulation dbConn) {
		super(dbConn);
	}

	/**
	 * 读取所有的参赛作品
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<HashMap<String, Object>> read(String staffCode) throws SQLException {
		String sql = "select" +
				  " v.c_id," +
				  " v.c_picname," +
				  " v.c_author," +
				  " v.c_votednum," +
				  " v.c_pic," +
				  " v.c_description," +
				  " if(vr.c_id is null,'投票','取消投票')" +
				  " from tb_vote v left join tb_voter vr" +
				  " on(v.c_id = vr.c_pic_id and vr.c_votercode = ?)" +
				  " order by v.c_id desc";
		String[] fields = {"id","picName","author","votedNumber","pic","description","voteState"};
		return readList(fields,sql,staffCode);
	}
	
	/**
	 * 新增作品
	 * @return
	 * @throws SQLException
	 */
	public boolean create(HashMap<String, Object> params) throws SQLException {
		String sql = "insert into tb_vote(" +
				" c_picname," +
				" c_author," +
				" c_pic," +
				" c_description" +
				" ) values (?,?,?,?)";
		Object[] args = {
				params.get("picName"),
				params.get("author"),
				params.get("pic"),
				params.get("description")
		};
		return update(sql, args);
	}

	/**
	 * 投票成功
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public boolean updateVoteNumber(int id) throws SQLException {
		String sql = " update" +
					 " tb_vote" +
					 " set c_votednum = c_votednum+1" +
					 " where c_id = ?";
		return update(sql,id);
	}
	
	/**
	 * 取消投票
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public boolean updateVoteNumber1(int id) throws SQLException {
		String sql = " update" +
					 " tb_vote" +
					 " set c_votednum = c_votednum-1" +
					 " where c_id = ?";
		return update(sql,id);
	}
	
	/**
	 * 判断是否投票过
	 * @param voterCode
	 * @return
	 * @throws SQLException
	 */
	public int readVotedCount(String voterCode) throws SQLException {
		String sql = "select count(1) " +
				" from tb_voter" +
				" where c_votercode = ?";
		return readCount(sql, voterCode);
	}
	
	/**
	 * 是否已投过该作品了 
	 * @param voterCode
	 * @return
	 * @throws SQLException
	 */
	public int readVotedCount1(String voterCode, int id) throws SQLException {
		String sql = "select count(1) " +
				" from tb_voter" +
				" where c_votercode = ?" +
				" and c_pic_id = ?";
		return readCount(sql, voterCode, id);
	}
	
	/**
	 * 新增投票人的投票记录
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public boolean createVoteRecord(int picId, String stateCode) throws SQLException {
		String sql = "insert into tb_voter(" +
				" c_pic_id," +
				" c_votercode," +
				" c_vote_time" +
				" ) values (?,?,?)";
		Object[] args = {
				picId,
				stateCode,
				new Date()
		};
		return update(sql, args);
	}
	
	/**
	 * 投票成功则异步更新该作品的投票数量
	 * @return
	 * @throws SQLException
	 */
	public HashMap<String, Object> readVoteNumberAfterSuccess(int id) throws SQLException {
		String sql = "select" +
				  " c_id," +
				  " c_votednum" +
				  " from tb_vote" +
				  " where c_id = ?";
		String[] fields = {"id","votedNumber"};
		return readMap(fields, sql, id);
	}

	public boolean update(HashMap<String, Object> params) throws SQLException {
		String sql = " update tb_vote" +
					" set c_picname = ?," +
					" c_author = ?," +
					" c_pic = ?," +
					" c_description = ?" +
					" where c_id = ?";
		Object[] args = {
				params.get("picName"),
				params.get("author"),
				params.get("pic"),
				params.get("description"),
				params.get("id")
		};
		return update(sql,args);
	}
	
	public boolean updatePic(HashMap<String, Object> params) throws SQLException {
		String sql = " update tb_vote" +
					" set c_pic = ?" +
					" where c_id = ?";
		Object[] args = {
				null,
				params.get("id")
		};
		return update(sql,args);
	}
	
	/**
	 * 通过id读
	 * @return
	 * @throws SQLException
	 */
	public HashMap<String, Object> readById(HashMap<String, Object> params) throws SQLException {
		String sql = "select" +
				  " c_id," +
				  " c_picname," +
				  " c_author," +
				  " c_votednum," +
				  " c_pic," +
				  " c_description" +
				  " from tb_vote" +
				  " where c_id = ?";
		String[] fields = {"id","picName","author","votedNumber","pic","description"};
		return readMap(fields,sql,params.get("id"));
	}
	
	public boolean deleteWork(HashMap<String, Object> params) throws SQLException {
		String sql = " delete from tb_vote" +
					" where c_id = ?";
		Object[] args = {
				params.get("id")
		};
		return update(sql,args);
	}
	
	public boolean deleteVoter(int id) throws SQLException {
		String sql = " delete from tb_voter" +
					" where c_pic_id = ?";
		return update(sql,id);
	}
	
}
