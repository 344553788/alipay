/**
 * @author hwh
 */
package com.alipay.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.StringUtils;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;

/**
 * @author ThinkPad User
 *
 */
public class Test {
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", "2014110600015763",
				"MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCU8GPk9PA87lUiaEv4gdlPhHzZZ48rtCqeorRtwvHGzHeeyy9rIce4+8t5KipiTzl3pFo53ERIXNXBAbggK5m9r2F3ampZ3FEFWGC/fsiv5aocjq9v8RC1P4Zk7PYPpGDKW44cfAc2S8z1FMlYQHhVlfTUyW4KctREx9+O6Hplj1Y38IqPAOruq0ASSYwwWmxUzUz5kQVWgrsf28KGhvrfvRZKxskTx2ehH/ugm49r58bkrxFz7kWvnyO9oEWcQPvl++vyCpOO1RAzuvO00YirUHTBc4oxvsKiBeRIULCq5c+WV6ROVMZ+oA7QeheH0AEDz0N8qHRvqc+NtN+6yPb5AgMBAAECggEAEzzNmlgs46HhKzWPgoS7HfM98RLYQkveKWlYpsjEU+o7FS6nbE4LrxAO4xKYMOSMxrBa2xhhVXtwPKLNNpKLJB4QF82N+Rdk+6zDqlhk8ZtXkqK3dF0NzGttH4If8jtIKTsUKwe2IsQ9r/ZEX3rMUDmzSvzNktnsIwoyDRJoJM/fkpw2i+Xg69Gvkq02Cl218fUykMKvAHbc3NHfofr8sn7YchQUweDHm4G2RYj93F7wQOAol7HaK3N/ZBeYl2BPopCYv6+Ni4wxNB8Nv2UBTPPYiUcHgYMObeDWujPYsV3cncj95GSLTGrKV49lZG1AQFe4qAb9eMQIK+d6Y9YsAQKBgQD+82oB12XbNcS7fBGJhYyZEGdElsSRraP3d7rXm/Lq6u7zf9NZq36YwwawqRigfAibNsrwtp4J1NoOd4rH3w0v+xLC5RvarwyWYY+opKeI28YcqLavKPD0KB27UVHYfcddmWwml4Ov8Obe58cySLwp1LDg62SWBSzOkRPzlEAagQKBgQCVjUtvpKGJ6Ogb4J7+WvjhdfYPdsA9u77Rk+eQll2LodWjQ4pwd5IZtVKbxDZ4GsLgFFbSgeeCXsG7Il5LLMyE6anx2Zo38t+1matAdv6zoceR2v8P8Rt4YPLwKzTUczQZY5qaX0ChbUVWL1o6FL/OqwWOZV06Fl6cxCWWAuRweQKBgALj8ZS5w27byjIx8uTzPafSlskhSHM6W7yHf/fHzdY5+aGLFnNSzWmf9Gh2UlAnd9WCpoRaHaqQ3jQByPIBJl4f45OQE2PETkk6K+3GiBu2GiXjB8cumKS7+7JO9rWUn3kBL0e0ugp5mbR3PjvktTVoMuUp5gcJYfupi2dci+mBAoGANh5fghMtcd6awBvU7W62ScCKPqZWgwCB2Q2Q6r2/NFxoFyDF5FUgxbTcJAopO+VTSAuUL7Fauc7YhIO3Y7Tefeqg2ShJqp11Xx4w3q8xFIgi1CFP09EIg9Nxln/MKmVWISnuNJbrqH8GbF3OZN5FWuCArjr4NaEKs+g3QcXiLOECgYEAqT4BoWnlILbgOyZZHZSlUXX7J3KLyQwMWC3kCZ9zqBbLaOOsX9DwIRJo4QjLGrtdvdhsNr7wxWZNKnoUC4jF45yJ8/I4mRrzAacc+hFwGEkWgtcAqjqNJcdH57SwCxhk8Prkm90B2f/Na6uW4g/eNBiSYsKRa4ac1I/HUXy1Eu8=",
				"json", "GBK",
				"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArWQGzCWxJ7+jmOHvTyrJpEuoVsCCT1T8naSfV0tB0wW5sHb1C/vFf1Z2AcsboIGoXs+Px3wZjbK6szG0YT7UU2jIK4SpM8Ze/x+cQFTvj5sRarR+JrubsxeTlJYRfboI44oeeEXQTy4Iw0jOkHZUE4rXnerZoWCn3fnGGSdTKGy05IvVh3y3h1kvCPNQfMsdqpJtSehm0rOmrDYOyP/RCB+jYjbjZmlP9JrucRow1Zx7ps1EsIH1V/weWp+pGgU5xs341sJVZMpsCMwF5ctdikZymSwRvzASyMxr8pMWAP10iEx+GDJ2n/5KJP6wLhM64xqXxKxrkjdt0v5+OwtwdQIDAQAB",
				"RSA2");
		String date = "2017-04-18";
		AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
		request.setBizContent("{" + "\"bill_type\":\"trade\",\"bill_date\":\"" + date + "\"}"); // 设置业务参数

		try {
			AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.execute(request);
			// 将接口返回的对账单下载地址传入urlStr
			String urlStr = response.getBillDownloadUrl();
			// 指定希望保存的文件路径
			String filePath = "E://a.zip";
			URL url = null;
			HttpURLConnection httpUrlConnection = null;
			InputStream fis = null;
			FileOutputStream fos = null;
			try {
				url = new URL(urlStr);
				httpUrlConnection = (HttpURLConnection) url.openConnection();
				httpUrlConnection.setConnectTimeout(5 * 1000);
				httpUrlConnection.setDoInput(true);
				httpUrlConnection.setDoOutput(true);
				httpUrlConnection.setUseCaches(false);
				httpUrlConnection.setRequestMethod("GET");
				httpUrlConnection.setRequestProperty("Charsert", "UTF-8");
				httpUrlConnection.connect();
				fis = httpUrlConnection.getInputStream();
				byte[] temp = new byte[1024];
				int b;
				fos = new FileOutputStream(new File(filePath));
				while ((b = fis.read(temp)) != -1) {
					fos.write(temp, 0, b);
					fos.flush();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fis != null)
						fis.close();
					if (fos != null)
						fos.close();
					if (httpUrlConnection != null)
						httpUrlConnection.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			CSVFileUtil csvUtil = null;
			for (File file : upzipFile("e://a.zip", "e://abc")) {
				if (!file.getName().contains("汇总")) {
					csvUtil = new CSVFileUtil(file.getAbsolutePath());
					break;
				}
			}
			boolean flag = true;
			if (csvUtil == null) {
				flag = false;
			}
			List<String> batchSql = new ArrayList<String>();
			StringBuffer sb = new StringBuffer();
			while (flag) {
				String lineContent = csvUtil.readLine();
				if (!StringUtils.isEmpty(lineContent) && lineContent.startsWith("20")) {
					sb.append(
							"INSERT INTO `hzc_finance`.`payment_alipay_bill`(trade_no,payment_no,trade_type,product_name,");
					sb.append(
							"trade_time,finish_time,shop_no,shop_name,operation_name,device_no,ali_account,total_fee,actual_fee,");
					sb.append("red_pack_fee,ali_integration,ali_discount,shop_discount,discount_fee,discount_name,");
					sb.append("red_pack_consume_fee,card_fee,refund_no,service_fee,profit_fee,remark) values('");
					sb.append(csvUtil.ignoreString(csvUtil.ignoreString(lineContent, "\t", ""), ",", "','"))
							.append("');");
					System.out.println(sb.toString());
					batchSql.add(sb.toString());
					sb.delete(0, sb.length());
					// sync_date
				}
				if (lineContent == null) {
					flag = false;
				}
			}
			generalInsert(batchSql);
			// Thread.sleep(3000);
			// deletefile("e://abc");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void generalInsert(List<String> batchSql) throws ClassNotFoundException, SQLException {
		long start = System.currentTimeMillis();
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://172.16.102.81:13306/hzc_finance", "root",
				"1Qazxcvbn");
		connection.setAutoCommit(false);
		PreparedStatement cmd = connection.prepareStatement("select 1");
		for (String sql : batchSql) {
			cmd = connection.prepareStatement(sql);
			cmd.executeUpdate();
		}
		connection.commit();
		cmd.close();
		connection.close();
		long end = System.currentTimeMillis();
		System.out.println(end - start);// 158918毫秒
	}

	/**
	 * 解压缩ZIP文件，将ZIP文件里的内容解压到targetDIR目录下
	 * 
	 * @param zipName
	 *            待解压缩的ZIP文件名
	 * @param targetBaseDirName
	 *            目标目录
	 */
	public static List<File> upzipFile(String zipPath, String descDir) {
		return upzipFile(new File(zipPath), descDir);
	}

	/**
	 * 对.zip文件进行解压缩
	 * 
	 * @param zipFile
	 *            解压缩文件
	 * @param descDir
	 *            压缩的目标地址，如：D:\\测试 或 /mnt/d/测试
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<File> upzipFile(File zipFile, String descDir) {
		List<File> _list = new ArrayList<File>();
		try {
			ZipFile _zipFile = new ZipFile(zipFile, "GBK");
			for (Enumeration entries = _zipFile.getEntries(); entries.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				File _file = new File(descDir + File.separator + entry.getName());
				if (entry.isDirectory()) {
					_file.mkdirs();
				} else {
					File _parent = _file.getParentFile();
					if (!_parent.exists()) {
						_parent.mkdirs();
					}
					InputStream _in = _zipFile.getInputStream(entry);
					OutputStream _out = new FileOutputStream(_file);
					int len = 0;
					byte[] _byte = new byte[1024];
					while ((len = _in.read(_byte)) > 0) {
						_out.write(_byte, 0, len);
					}
					_in.close();
					_out.flush();
					_out.close();
					_list.add(_file);
				}
			}
		} catch (IOException e) {
		}
		return _list;
	}

	/**
	 * 对临时生成的文件夹和文件夹下的文件进行删除
	 */
	public static void deletefile(String delpath) {
		try {
			File file = new File(delpath);
			if (!file.isDirectory()) {
				file.delete();
			} else if (file.isDirectory()) {
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File delfile = new File(delpath + File.separator + filelist[i]);
					if (!delfile.isDirectory()) {
						delfile.delete();
					} else if (delfile.isDirectory()) {
						deletefile(delpath + File.separator + filelist[i]);
					}
				}
				// file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
