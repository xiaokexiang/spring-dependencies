package io.spring.common.io;

import io.spring.common.response.ResponseBody;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xiaokexiang
 * @since 2020/12/18
 */
@RestController
@RequestMapping("/image")
public class ImageController {

    @Resource
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseBody<String> upload(MultipartFile file) {
        return ResponseBody.ok(imageService.saveImage(file));
    }

    /**
     * 返回二进制类型的图片
     */
    @GetMapping("/view/{id}")
    public void getImage(@PathVariable String id, HttpServletResponse response) {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("Image id can not be null");
        }
        imageService.getImage(id, response, false);
    }

    /**
     * 返回图片响应流，可地址栏查看
     */
    @GetMapping("/download/{id}")
    public void download(@PathVariable String id, HttpServletResponse response) {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("Image id can not be null");
        }
        imageService.getImage(id, response, true);
    }
}
