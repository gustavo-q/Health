package org.csource.fastdfs;


import java.io.IOException;

public class Test2 {

    public static final String CONF_FILENAME = "fdfs_client.conf"; //配置文件
    public static final String URL = "http://172.20.10.5"; //访问路径

    public static final String IMG_PATH = "/Users/gustavo/Pictures/wallpaper/1.jpg";  //测试文件上传
    public static final String FILE_EXTNAME = "jpg";                  //测试扩展名

    public static final String DOWNLOAD = "/Users/gustavo/Pictures/test.jpg"; //下载

    public static void main(String[] args) {
        //上传
        String[] upload = upload(IMG_PATH, FILE_EXTNAME);
        String path = pathSplicing(upload);
        System.out.println(path);
        //下载
        int download = download(upload[0], upload[1], DOWNLOAD);
        System.out.println(download == 0 ? "成功" : "失败");
        //删除
        int delete = delete(upload[0], upload[1]);
        System.out.println(delete == 0 ? "成功" : "失败");
    }

    /**
     * 文件删除
     *
     * @param groupName 文件组名
     * @param filename  文件路径
     * @return 返回值：0:成功， 其他：失败
     */
    private static int delete(String groupName, String filename) {
        StorageClient sc = null;
        int result = -1;
        try {
            //初始化,读取配置文件
            ClientGlobal.init(CONF_FILENAME);
            TrackerClient tc = new TrackerClient();
            TrackerServer ts = tc.getTrackerServer();
            StorageServer ss = tc.getStoreStorage(ts);
            //操作文件
            sc = new StorageClient(ts, ss);
            //组名，文件名
            result = sc.delete_file(groupName, filename);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sc != null) {
                try {
                    sc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 文件下载
     *
     * @param groupName    下载文件组名
     * @param image        下载文件路径
     * @param downloadPath 保存地址/文件名
     * @return 返回值：0:成功， 其他：失败
     */
    private static int download(String groupName, String image, String downloadPath) {
        StorageClient sc = null;
        int result = -1;
        try {
            //初始化,读取配置文件
            ClientGlobal.init(CONF_FILENAME);
            TrackerClient tc = new TrackerClient();
            TrackerServer ts = tc.getTrackerServer();
            StorageServer ss = tc.getStoreStorage(ts);
            //操作文件
            sc = new StorageClient(ts, ss);
            //下载文件组名， 下载文件路径，  保存路径/文件名
            result = sc.download_file(groupName, image, downloadPath);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sc != null) {
                try {
                    sc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    /**
     * 文件上传
     *
     * @param imgPath     文件路径
     * @param fileExtName 文件扩展名
     * @return 返回值：[0]：组名， [1]:路径
     */
    public static String[] upload(String imgPath, String fileExtName) {
        StorageClient sc = null;
        String[] result = null;
        try {
            //初始化,读取配置文件
            ClientGlobal.init(CONF_FILENAME);
            TrackerClient tc = new TrackerClient();
            TrackerServer ts = tc.getTrackerServer();
            StorageServer ss = tc.getStoreStorage(ts);
            //操作文件
            sc = new StorageClient(ts, ss);
            //文件绝对路径， 文件扩展名， 属性文件(不做上传),
            result = sc.upload_appender_file(imgPath, fileExtName, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sc != null) {
                try {
                    sc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 路径拼接
     *
     * @param result 数组：[0]：组名， [1]:路径
     * @return 文件访问路径
     */
    public static String pathSplicing(String[] result) {
        String path = "";
        try {
            if (result != null && result.length > 0) {
                StringBuilder url = new StringBuilder();
                url.append(URL);
                for (String str : result) {
                    url.append("/");
                    url.append(str);
                }
                path = url.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }
}
