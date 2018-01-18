package com.ok8.front.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.ok8.common.db.DBManipulation;
import com.ok8.common.global.ErrorCode;
import com.ok8.common.utils.StringUtils;
import com.ok8.front.dao.VoteDao;

/**
 * 摄影比赛投票系统
 */
public class VoteService {
	
	public ArrayList<HashMap<String, Object>> read(String staffCode){
		ArrayList<HashMap<String, Object>> datas = null;
		DBManipulation dbConn = null;
		try {
			dbConn = new DBManipulation();
			VoteDao voteDao = new VoteDao(dbConn);
			if (staffCode==null) {
				staffCode = "";
			}
			datas = voteDao.read(staffCode);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (dbConn != null) {
				dbConn.disConnect();
			}
		}
		return datas;
	}

	/**
	 * 员工投票
	 * @param params
	 * @return
	 */
	public HashMap<String, Object> vote(HashMap<String, Object> params) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		DBManipulation dbConn = null;
		try {
			dbConn = new DBManipulation(false);
			VoteDao voteDao = new VoteDao(dbConn);
			int id = (int) params.get("id");
			String voterCode = (String) params.get("staffCode");
			if (StringUtils.isBlank(voterCode)) {
				//判断是否登陆
				data.put("errorCode", "2");
				dbConn.rollback();
				return data;
			}
			//取消投票
			int votedCount = voteDao.readVotedCount1(voterCode,id);
			if (votedCount > 0) {
				//如果投票过则返回提示信息
				voteDao.deleteVoter(id);
				voteDao.updateVoteNumber1(id);
				data.put("errorCode", ErrorCode.SUCCESS);
				dbConn.commit();
				return data;
			}
			//判断该员工是否投票过
			int votedCount1 = voteDao.readVotedCount(voterCode);
			if (votedCount1 > 0) {
				//如果投票过则返回提示信息
				data.put("errorCode", ErrorCode.ALREADYVOTE);
				dbConn.rollback();;
				return data;
			}
			//更新投票数量
			voteDao.updateVoteNumber(id);
			//插入投票人投票记录
			voteDao.createVoteRecord(id, voterCode);
			data = voteDao.readVoteNumberAfterSuccess(id);
			data.put("errorCode", ErrorCode.SUCCESS);
			dbConn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (dbConn != null) {
				dbConn.disConnect();
			}
		}
		return data;
	}
	
	/**
	 * 新增作品
	 * @param params
	 * @return
	 */
	public HashMap<String, Object> create(HttpServletRequest request) {
		// 得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
		String savePath = request.getServletContext().getRealPath(
				"/page/images");
		File savePathFile = new File(savePath);
		if (!savePathFile.exists()) {
			// 创建临时目录
			savePathFile.mkdir();
		}
		HashMap<String, Object> data = new HashMap<String,Object>();
		DBManipulation dbConn = null;
		try {
			dbConn = new DBManipulation();
			VoteDao voteDao = new VoteDao(dbConn);
//			// 使用Apache文件上传组件处理文件上传步骤：
//			// 1、创建一个DiskFileItemFactory工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 2、创建一个文件上传解析器
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 解决上传文件名的中文乱码
			upload.setHeaderEncoding("UTF-8");
			// 3、判断提交上来的数据是否是上传表单的数据
			if (!ServletFileUpload.isMultipartContent(request)) {
				// 按照传统方式获取数据
				data.put("errorCode", 1);
				return data;
			}
			// 4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
			List<FileItem> list = upload.parseRequest(request);
			HashMap<String, Object> params = new HashMap<String, Object>();
			for (FileItem item : list) {
				// 如果fileitem中封装的是普通输入项的数据
				if (item.isFormField()) {
					String name = item.getFieldName();
					// 解决普通输入项的数据的中文乱码问题
					String value = item.getString("UTF-8");
					params.put(name, value);
				} else {// 如果fileitem中封装的是上传文件
						// 得到上传的文件名称，
					String filename = item.getName();
					if (filename == null || filename.trim().equals("")) {
						continue;
					}
					// 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					filename = filename.substring(filename.lastIndexOf("\\") + 1);
					// 得到上传文件的扩展名
					String fileExtName = filename.substring(filename.lastIndexOf(".") + 1);
					// 获取item中的上传文件的输入流
					InputStream in = item.getInputStream();
					String savePicName = makeFileName();
					String picUrl = savePath+"/"+savePicName+fileExtName;
					String picFileName = savePicName+fileExtName;
					params.put("pic", picFileName);
					FileOutputStream out = new FileOutputStream(picUrl);
					// 创建一个缓冲区
					byte buffer[] = new byte[1024];
					// 判断输入流中的数据是否已经读完的标识
					int len = 0;
					// 循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
					while ((len = in.read(buffer)) > 0) {
						out.write(buffer, 0, len);
					}
					// 关闭输入流
					in.close();
					// 关闭输出流
					out.close();
				}
			}
			//保存传过来的信息
			voteDao.create(params);
			data.put("errorCode", 0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (dbConn != null) {
				dbConn.disConnect();
			}
		}
		return data;
	}
	
	/**
	 * 新增作品
	 * @param params
	 * @return
	 */
	public HashMap<String, Object> update(HttpServletRequest request) {
		// 得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
		String savePath = request.getServletContext().getRealPath("/page/images");
		File savePathFile = new File(savePath);
		if (!savePathFile.exists()) {
			// 创建临时目录
			savePathFile.mkdir();
		}
		boolean flag = false;
		HashMap<String, Object> data = new HashMap<String,Object>();
		DBManipulation dbConn = null;
		try {
			dbConn = new DBManipulation();
			VoteDao voteDao = new VoteDao(dbConn);
//			// 使用Apache文件上传组件处理文件上传步骤：
//			// 1、创建一个DiskFileItemFactory工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 2、创建一个文件上传解析器
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 解决上传文件名的中文乱码
			upload.setHeaderEncoding("UTF-8");
			// 3、判断提交上来的数据是否是上传表单的数据
			if (!ServletFileUpload.isMultipartContent(request)) {
				// 按照传统方式获取数据
				data.put("errorCode", 1);
				return data;
			}
			// 4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
			List<FileItem> list = upload.parseRequest(request);
			HashMap<String, Object> params = new HashMap<String, Object>();
			for (FileItem item : list) {
				// 如果fileitem中封装的是普通输入项的数据
				if (item.isFormField()) {
					String name = item.getFieldName();
					// 解决普通输入项的数据的中文乱码问题
					String value = item.getString("UTF-8");
					params.put(name, value);
				} else {// 如果fileitem中封装的是上传文件
						// 得到上传的文件名称，
					String filename = item.getName();
					if (filename == null || filename.trim().equals("")) {
						flag = true;
						continue;
					}
					// 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					filename = filename.substring(filename.lastIndexOf("\\") + 1);
					// 得到上传文件的扩展名
					String fileExtName = filename.substring(filename.lastIndexOf(".") + 1);
					// 获取item中的上传文件的输入流
					InputStream in = item.getInputStream();
					String savePicName = makeFileName();
					String picUrl = savePath+"/"+savePicName+fileExtName;
					String picFileName = savePicName+fileExtName;
					params.put("pic", picFileName);
					FileOutputStream out = new FileOutputStream(picUrl);
					// 创建一个缓冲区
					byte buffer[] = new byte[1024];
					// 判断输入流中的数据是否已经读完的标识
					int len = 0;
					// 循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
					while ((len = in.read(buffer)) > 0) {
						out.write(buffer, 0, len);
					}
					// 关闭输入流
					in.close();
					// 关闭输出流
					out.close();
				}
			}
			HashMap<String, Object> work = voteDao.readById(params);
			if (flag) {
				params.put("pic", work.get("pic"));
			} else {
				String path = savePath+"\\"+work.get("pic");
				File file = new File(path); // fileName已经包含文件夹名称
				file.delete();
			}
			//保存传过来的信息
			voteDao.update(params);
			data.put("errorCode", 0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (dbConn != null) {
				dbConn.disConnect();
			}
		}
		return data;
	}

	/**
	 * @Method: makeFileName
	 * @Description: 生成上传文件的文件名，文件名以：uuid+"_"+文件的原始名称
	 * @Anthor:孤傲苍狼
	 * @param filename
	 *            文件的原始名称
	 * @return uuid+"_"+文件的原始名称
	 */
	private String makeFileName() { // 2.jpg
		// 为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
		return UUID.randomUUID().toString()+".";
	}
	
	public HashMap<String, Object> deletePic(HashMap<String, Object> params, String savePath) {
		DBManipulation dbConn = null;
		try {
			dbConn = new DBManipulation(false);
			VoteDao voteDao = new VoteDao(dbConn);
			HashMap<String, Object> work = voteDao.readById(params);
			String path = savePath+"\\"+work.get("pic");
			voteDao.updatePic(params);
			File file = new File(path); // fileName已经包含文件夹名称
			file.delete();
			dbConn.commit();
		} catch (SQLException e) {
			if (dbConn != null) {
				dbConn.rollback();
			}
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (dbConn != null) {
				dbConn.disConnect();
			}
		}
		return null;
	}
	
	public HashMap<String, Object> deleteWork(HashMap<String, Object> params, String savePath) {
		DBManipulation dbConn = null;
		try {
			dbConn = new DBManipulation(false);
			VoteDao voteDao = new VoteDao(dbConn);
			HashMap<String, Object> work = voteDao.readById(params);
			String path = savePath+"\\"+work.get("pic");
			voteDao.deleteWork(params);
			int id = (int)params.get("id");
			voteDao.deleteVoter(id);
			File file = new File(path); // fileName已经包含文件夹名称
			file.delete();
			dbConn.commit();
		} catch (SQLException e) {
			if (dbConn != null) {
				dbConn.rollback();
			}
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (dbConn != null) {
				dbConn.disConnect();
			}
		}
		return null;
	}

}
