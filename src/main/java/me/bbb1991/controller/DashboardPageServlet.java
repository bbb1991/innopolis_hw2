package me.bbb1991.controller;

import me.bbb1991.helpers.CustomException;
import me.bbb1991.helpers.Helper;
import me.bbb1991.model.Book;
import me.bbb1991.model.User;
import me.bbb1991.service.BookService;
import me.bbb1991.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Objects;

/**
 * Created by bbb1991 on 12/24/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */

@Controller
@PropertySource("classpath:application.properties")
public class DashboardPageServlet {

    private static final Logger logger = LoggerFactory.getLogger(DashboardPageServlet.class);

    private BookService bookService;

    private UserService userService;

    private Helper helper;

    @Value("${avatar_folder}")
    private String avatarPath;

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String getDashboardPage(Model model) {

        User user = helper.getCurrentUser();

        List<Book> books = bookService.getBooksByAuthor(user.getUsername());


        model.addAttribute("user", user);
        model.addAttribute("books", books);

        return "dashboard";
    }

    /**
     * Метод для смены пароля текущего пользователя
     *
     * @param oldPassword     текущий пароль пользователя
     * @param password        новый пароль пользователя
     * @param confirmPassword повтор нового пароля пользователя
     * @param result
     * @return
     */
    @RequestMapping(value = "/dashboard/change_password", method = RequestMethod.POST)
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("password") String password,
                                 @RequestParam("confirmPassword") String confirmPassword, BindingResult result) {

        User user = helper.getCurrentUser();

        if (!Objects.equals(user.getPassword(), helper.getPasswordHash(oldPassword))) {
            result.addError(new ObjectError("oldPassword", "Current password incorrect!"));
        }


        if (!Objects.equals(password, confirmPassword)) { // сверка нового пароля
            result.addError(new ObjectError("confirmPassword", "Passwords not match!"));
            return "dashboard";
        }

        user.setPassword(helper.getPasswordHash(password));

        userService.saveOrUpdateUser(user);

        return "redirect:/dashboard";
    }

    /**
     * Загрузка аватара пользователя
     *
     * @param multipartFile
     * @return
     */
    @RequestMapping(value = "/dashboard/avatar", method = RequestMethod.POST)
    public String uploadAvatar(@RequestParam("avatar") MultipartFile multipartFile) {

        // fixme: 12/24/16 do something with this mess

        logger.info("Working with loaded file: {}", multipartFile);

        File path = new File(avatarPath);

        if (!path.exists()) {
            path.mkdirs();
        }

        User user = helper.getCurrentUser();

        logger.info("Saving to: {}", path + File.separator + multipartFile.getOriginalFilename());

        if (!multipartFile.isEmpty()) {

            try (BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(path + File.separator + multipartFile.getOriginalFilename()))) {

                stream.write(multipartFile.getBytes());

                logger.info("You successfully uploaded file");
            } catch (Exception e) {
                logger.error("error while saving file: {}", multipartFile, e);
                throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        } else {
            logger.info("Loaded file is empty");
        }

        user.setAvatar("avatars" + File.separator + multipartFile.getOriginalFilename());

        userService.saveOrUpdateUser(user);

        return "redirect:/dashboard";
    }

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    public void setHelper(Helper helper) {
        this.helper = helper;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
