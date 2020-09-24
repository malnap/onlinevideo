package com.duyi.onlinevideo.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.duyi.onlinevideo.dto.ResponseResult;
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

/**
 * 该类实现了验证码的生成和验证
 */
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

    /**
     * 该方法用于绘制验证码,同时将验证码保存在session作用域中方便数据比对
     * @param session session对于保存验证码
     * @param response 响应该验证码图片给浏览器
     */
    @ResponseBody
    @RequestMapping("/vcode")
    public void vcode(HttpSession session, HttpServletResponse response) {

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
        String sVcode = lineCaptcha.getCode();

        /* 将字符串验证码保存到session中,据进行比对 */
        session.setAttribute("session_vcode", sVcode);

        /* 输出流将数据写回给浏览器 */
        try {
            ServletOutputStream out = response.getOutputStream();
            lineCaptcha.write(out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 校验前台输入的数据与session中的数据
     * @param vcode 前台输入的数据
     * @param session session中的真实数据
     * @return 返回一个json格式的对象
     */
    @ResponseBody
    @RequestMapping("/checkVcode")
    public ResponseResult checkVcode(String vcode,HttpSession session) {

        /* 默认验证码无效 */
        ResponseResult<String> result = new ResponseResult<>(-1, "vcode invalid");

        /* 拿到保存在session中的验证码,与用户输入的进行比对 */
        String sVcode = (String) session.getAttribute("session_vcode");

        /* 如果两者有一个为空 或 前台数据与session中真实数据不匹配 */
        if (StrUtil.isEmpty(vcode) || StrUtil.isEmpty(sVcode) || !vcode.equals(sVcode)) {
            /* 返回错误的响应代号 */
            return result;
        }

        /* 重新设置验证码有效 */
        result.setRcode(1);
        result.setMessage("ok");
        return result;
    }
}
