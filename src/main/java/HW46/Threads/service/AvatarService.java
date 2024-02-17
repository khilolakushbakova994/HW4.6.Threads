package HW46.Threads.service;


import HW46.Threads.model.Avatar;
import HW46.Threads.model.Student;
import HW46.Threads.repository.AvatarRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {

    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);
    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    private final StudentService studentService;
    private final AvatarRepository avatarRepository;

    public AvatarService(StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    public void uploadImage(Long studentId, MultipartFile file) throws IOException {
        Student student = studentService.find(studentId);

        Path filePath = Path.of(avatarsDir, studentId + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try
                (InputStream is = file.getInputStream();
                 OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                 BufferedInputStream bis = new BufferedInputStream(is, 1024);
                 BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
                ) {
            bis.transferTo(bos);
        }

        Avatar avatar = findAvatarById(studentId);
        if (avatar==null){
            avatar= new Avatar();
        }
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());

        avatarRepository.save(avatar);

    }

    public Avatar findAvatarById(Long studentId) {
        Avatar avatar=  avatarRepository.findById(studentId).orElse(null);
        logger.info("Was invoked method for findAvatarById");
        if (avatar == null) {
            logger.error("There is no avatar with id = {}", studentId);
        }

        return avatar;
    }

    private byte[] generateImagePreview(Path filePath) throws IOException {

        try ( InputStream is = Files.newInputStream(filePath);
              BufferedInputStream bis = new BufferedInputStream(is, 1024);
              ByteArrayOutputStream baos = new ByteArrayOutputStream())
        {
            BufferedImage image = ImageIO.read(bis);

            int height  = image.getHeight() / (image.getWidth()/100);
            BufferedImage preview  = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0,0,100, height, null);
            graphics.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }

    private String getExtension(String filename){
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

}