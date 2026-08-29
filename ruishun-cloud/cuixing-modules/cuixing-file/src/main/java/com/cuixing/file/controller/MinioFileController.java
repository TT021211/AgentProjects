//package com.cuixing.file.controller;
//
//import com.cuixing.common.core.utils.DateUtils;
//import com.cuixing.common.core.utils.StringUtils;
//import com.cuixing.common.core.web.domain.AjaxResult;
//import com.cuixing.file.utils.MinioUtil;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.tomcat.util.http.fileupload.IOUtils;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletResponse;
//import java.io.InputStream;
//import java.net.URLEncoder;
//
///**
// * @Author wupuj
// * @create 2024/7/21 10:42
// */
//
//@Api(tags = "文件操作接口")
//@RestController
//@RequestMapping(value = "/minio/file")
//@RequiredArgsConstructor
//@Slf4j
//public class MinioFileController {
//
//    private final MinioUtil minioUtil;
//
//    @ApiOperation("上传一个文件")
//    @RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
//    public AjaxResult fileupload(@RequestParam MultipartFile uploadfile, @RequestParam String bucket,
//                                 @RequestParam(required = false) String objectName) throws Exception {
//        minioUtil.createBucket(bucket);
//        InputStream inputStream = uploadfile.getInputStream();
//        String format = StringUtils.format("{}", DateUtils.datePath());
//        objectName = objectName != null ? format + "/" + objectName : format;
//        minioUtil.uploadFile(inputStream, bucket, objectName + "/" + uploadfile.getOriginalFilename());
//        String objectInfo = minioUtil.getPresignedObjectUrl(bucket, objectName + "/" + uploadfile.getOriginalFilename(), 60 * 60 * 24 * 7);
//        inputStream.close();
//
//        return AjaxResult.success(objectInfo);
//    }
//
//    @ApiOperation("列出所有的桶")
//    @RequestMapping(value = "/listBuckets", method = RequestMethod.GET)
//    public AjaxResult listBuckets() throws Exception {
//        return AjaxResult.success(minioUtil.listBuckets());
//    }
//
//    @ApiOperation("递归列出一个桶中的所有文件和目录")
//    @RequestMapping(value = "/listFiles", method = RequestMethod.GET)
//    public AjaxResult listFiles(@RequestParam String bucket) throws Exception {
//        return AjaxResult.success(minioUtil.listFiles(bucket));
//    }
//
//    @ApiOperation("下载一个文件")
//    @RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
//    public void downloadFile(@RequestParam String bucket, @RequestParam String objectName,
//                             HttpServletResponse response) throws Exception {
//        InputStream stream = minioUtil.download(bucket, objectName);
//        ServletOutputStream output = response.getOutputStream();
//        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(objectName.substring(objectName.lastIndexOf("/") + 1), "UTF-8"));
//        response.setContentType("application/octet-stream");
//        response.setCharacterEncoding("UTF-8");
//        IOUtils.copy(stream, output);
//    }
//
//
//    @ApiOperation("删除一个文件")
//    @RequestMapping(value = "/deleteFile", method = RequestMethod.GET)
//    public AjaxResult deleteFile(@RequestParam String bucket, @RequestParam String objectName) throws Exception {
//        minioUtil.deleteObject(bucket, objectName);
//        return AjaxResult.success();
//    }
//
//    @ApiOperation("删除一个桶")
//    @RequestMapping(value = "/deleteBucket", method = RequestMethod.GET)
//    public AjaxResult deleteBucket(@RequestParam String bucket) throws Exception {
//        minioUtil.deleteBucket(bucket);
//        return AjaxResult.success();
//    }
//
//
//    @ApiOperation("复制一个文件")
//    @GetMapping("/copyObject")
//    public AjaxResult copyObject(@RequestParam String sourceBucket, @RequestParam String sourceObject, @RequestParam String targetBucket, @RequestParam String targetObject) throws Exception {
//        minioUtil.copyObject(sourceBucket, sourceObject, targetBucket, targetObject);
//        return AjaxResult.success();
//    }
//
//    @GetMapping("/getObjectInfo")
//    @ApiOperation("获取文件信息")
//    public AjaxResult getObjectInfo(@RequestParam String bucket, @RequestParam String objectName) throws Exception {
//
//        return AjaxResult.success(minioUtil.getObjectInfo(bucket, objectName));
//    }
//
//    @GetMapping("/getPresignedObjectUrl")
//    @ApiOperation("获取一个连接以供下载")
//    public AjaxResult getPresignedObjectUrl(@RequestParam String bucket, @RequestParam String objectName, @RequestParam Integer expires) throws Exception {
//
//        return AjaxResult.success(minioUtil.getPresignedObjectUrl(bucket, objectName, expires));
//    }
//
//    @GetMapping("/listAllFile")
//    @ApiOperation("获取minio中所有的文件")
//    public AjaxResult listAllFile() throws Exception {
//
//        return AjaxResult.success(minioUtil.listAllFile());
//    }
//
//}
