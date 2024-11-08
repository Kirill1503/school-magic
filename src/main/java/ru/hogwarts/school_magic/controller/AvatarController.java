package ru.hogwarts.school_magic.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school_magic.model.Avatar;
import ru.hogwarts.school_magic.service.AvatarService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/avatar")
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "{id}/download", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveAvatar(@PathVariable("id") Integer studentId,
                                             @RequestParam MultipartFile file) throws IOException {
        avatarService.saveAvatar(studentId, file);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}/get/data")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable("id") Integer id) {
        Avatar avatar = avatarService.getAvatar(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping("{id}/get")
    public void downloadAvatar(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.getAvatar(id);

        Path path = Path.of(avatar.getFilePath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream()) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLengthLong(avatar.getFileSize());
            is.transferTo(os);
        }
    }

    @GetMapping("/get/allAvatars")
    public List<Avatar> getImages(
            @RequestParam int page,
            @RequestParam int size) {
        return avatarService.getAllAvatars(page, size);
    }
}
