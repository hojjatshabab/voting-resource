package com.ream.core.controller.auth;


import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.ream.core.controller.ActionResult;
import com.ream.core.controller.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Locale;
import java.util.Objects;

@RestController
@RequestMapping("api/captcha")
@Tag(name = "کلاس public برای دریافت captcha", description = " حتما در درخاست بعدی برای احراز کپتچا از  header با این نام Y2FwdGNoYQ و مقداری ک میدهد به صورت رمز نگاری شده است استفاده کنید ")
public class CaptchaController extends BaseController {

    private final DefaultKaptcha defaultKaptcha;
    @Autowired
    private jakarta.servlet.http.HttpServletRequest request;
    @Autowired
    private jakarta.servlet.http.HttpServletResponse response;

    @Autowired
    public CaptchaController(DefaultKaptcha defaultKaptcha) {
        this.defaultKaptcha = defaultKaptcha;
    }

    @GetMapping("/get-captcha")
    @Operation(summary = "گرفتن کپچا", description = "یک تصویر")
    public void getCaptcha() throws IOException {

        String text = defaultKaptcha.createText();
        BufferedImage image = defaultKaptcha.createImage(text);

        request.getSession().setAttribute("captcha", text);
        response.addHeader("Y2FwdGNoYQ", Base64.getEncoder().encodeToString(text.getBytes()));
        response.addHeader("Access-Control-Expose-Headers", "Y2FwdGNoYQ");
        response.setContentType("image/jpeg");
        OutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        outputStream.close();

    }


    @PostMapping("/verify-captcha")
    @Operation(summary = "احراز هویت گپچا", description = "Boolean")
    public Boolean verifyCaptcha(@RequestParam("captcha") String captcha, Locale locale) {
        String captchaSession = null;
        captchaSession = request.getHeader("Y2FwdGNoYQ");
        if (Objects.isNull(captchaSession)) {
            captchaSession = (String) request.getSession().getAttribute("captcha");
        }
        if (Objects.nonNull(captchaSession)) {
            byte[] decodedBytes = Base64.getDecoder().decode(captchaSession);
            captchaSession = new String(decodedBytes);
        }
        if (Objects.nonNull(captchaSession) && Objects.nonNull(captcha) && !captcha.isEmpty() && !captcha.isBlank())
            if (captcha.toLowerCase().equals(captchaSession.toLowerCase())) {
                return true;
            } else {
                return false;
            }
        return false;
    }

}