package com.zj.caoshangfei.common.uploadFile;

import com.zj.caoshangfei.GlobalConstant;
import com.zj.caoshangfei.common.RestResult;
import com.zj.caoshangfei.config.OSSProperties;
import com.zj.caoshangfei.integration.aliyunoss.AliyunOssClient;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import static com.zj.caoshangfei.GlobalConstant.ADMIN_MAPPING;

@RequestMapping(ADMIN_MAPPING + "/common")
@RestController
public class UploadFileRestService {

    private final Logger logger = LoggerFactory.getLogger(UploadFileRestService.class);


    @Autowired
    private AliyunOssClient aliyunOssClient;

    @Autowired
    private OSSProperties ossProperties;

    private final String SEPARATOR = "/";


    @PostMapping(value = "/uploadfile")
    public RestResult post(MultipartFile _file, String moduleName) {
        RestResult restResult = new RestResult();

        //modulename/yyyyMM/dd/uuid+file extention name
        String originalFilename = _file == null ? "" : _file.getOriginalFilename();
        if ("".equals(originalFilename) || StringUtils.isBlank(moduleName)) {
            restResult.setCode(RestResult.ResultCodeEnum.EMPTY_FILE.getCode());
            restResult.setMsg("文件为空或者模块名为空");
            return restResult;
        }


        try {
            InputStream inputStream = _file.getInputStream();
            String suffix = _file.getOriginalFilename().substring(_file.getOriginalFilename().indexOf("."));
            String fileName = UUID.randomUUID().toString() + suffix;

            String date = DateFormatUtils.format(new Date(), "yyyyMMdd");
            String filePath = new StringBuffer(GlobalConstant.OSS_STORE_ROOT_PATH).append(SEPARATOR).append(moduleName).append(SEPARATOR)
                    .append(StringUtils.substring(date, 0, 6)).append(SEPARATOR)
                    .append(StringUtils.substring(date, 6, 8)).append(SEPARATOR)
                    .append(fileName).toString();

            //这里默认传到图片的bucket
            String fileUrl = aliyunOssClient.uploadByStream(ossProperties.getImage(), filePath, inputStream);

            restResult.setCode(RestResult.ResultCodeEnum.SUCC.getCode());
            JSONObject obj = new JSONObject();
            obj.put("url", fileUrl);//网络地址
            obj.put("fpath", filePath);//相对地址
            restResult.setData(obj);

        } catch (IOException e) {
            logger.error("Oss 上传文件失败：" + e.getMessage());
            restResult.setCode(RestResult.ResultCodeEnum.ERROR.getCode());
            restResult.setMsg(e.getMessage());
        }

        return restResult;
    }


}
