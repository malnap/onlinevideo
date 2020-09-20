package com.duyi.onlinevideo.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.util.RandomUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class VcodeController {

    char[] vcodeSequence = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    @ResponseBody
    @RequestMapping("/vcode1")
    public void vcode(HttpServletRequest request, HttpServletResponse response) {

        /* 设置生成图片的属性,宽度,高度,图片类型 */
        BufferedImage bufferedImage = new BufferedImage(80, 30, BufferedImage.TYPE_INT_RGB);

        Graphics2D gd = bufferedImage.createGraphics();

        gd.setColor(Color.WHITE);
        gd.drawRect(0, 0, 80, 30);

        Font font = new Font("宋体", Font.BOLD, 25);
        gd.setFont(font);

        /* 保存验证码 */
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            String randomStr = String.valueOf(vcodeSequence[RandomUtil.randomInt(10)]);
            sb.append(randomStr);
            /* 红绿蓝 */
            gd.setColor(new Color(RandomUtil.randomInt(255), RandomUtil.randomInt(255), RandomUtil.randomInt(255)));
            gd.drawString(randomStr, (i + 1) * 15, 25);
        }

        /* 将验证码保存到session,方便验证正确性 */
        HttpSession session = request.getSession(true);
        session.setAttribute("session_vcode", sb.toString());

        /* 设置HTTP协议响应头,图片类型 */
        response.setContentType("image/jpeg");
        /* 不使用缓存 */
        response.setDateHeader("Expires", -1);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        try {
            /* 获取输出流 */
            ServletOutputStream os = response.getOutputStream();
            ImageIO.write(bufferedImage, "jpeg", os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @ResponseBody
    @RequestMapping("/vcode")
    public void vode1(HttpSession session, HttpServletResponse response) {
        /* 自定义纯数字的验证码（随机4位数字,可重复）*/
        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 4);

        /* 设置HTTP协议响应头,图片类型 */
        response.setContentType("image/jpeg");
        /* 不使用缓存 */
        response.setDateHeader("Expires", -1);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        /* 设置验证码的宽度与高度 */
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(100, 50);

        /* 设置验证码生成器 */
        lineCaptcha.setGenerator(randomGenerator);

        /* 重新生成code */
        lineCaptcha.createCode();

        /* 拿到生成的验证码 */
        String v_code = lineCaptcha.getCode();

        /* 将字符串验证码保存到session中 */
        session.setAttribute("v_code", v_code);

        try {
            ServletOutputStream out = response.getOutputStream();
            lineCaptcha.write(out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
