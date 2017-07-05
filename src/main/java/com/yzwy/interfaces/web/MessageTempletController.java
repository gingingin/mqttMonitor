package com.yzwy.interfaces.web;

import com.yzwy.application.MessageService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by gin on 2017/6/26.
 */
@Controller
@RequestMapping("/templet")
public class MessageTempletController {
    @Autowired
    MessageService messageService;

    private static final Logger LOG = LoggerFactory.getLogger(MessageTempletController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String jump( Model model) {
//        model.addAttribute("name", name);
        return "message/templet";
    }



    @ResponseBody
    @RequestMapping(value = "generate",method = RequestMethod.GET)
    public void generateTempletJson() throws IOException {
        messageService.caclulateAllTemplet();
        messageService.generateTempletJson();
    }

    @ResponseBody
    @RequestMapping(value = "download",method = RequestMethod.GET)
    public void downloadTempletJson(HttpServletResponse response) throws IOException {
        messageService.caclulateAllTemplet();
        messageService.generateTempletJson();
        String path = this.getClass().getClassLoader().getResource("msg_templet.json").getPath();

        File file = new File(path);

            if (file.exists()) {
                response.setContentType("application/force-download; charset=utf-8");// 设置强制下载不打开
                response.addHeader("Content-Disposition",
                        "attachment;fileName="+file.getName());// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                System.out.println("文件不存在："+file.getCanonicalPath());
            }

    }

}
