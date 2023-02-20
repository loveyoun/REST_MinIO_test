package RESTuploadtest.RESTuploadtest;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/")
public class UploadController {

    @Value("${part4.upload.path}")
    private String uploadPath;

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucket;


    @GetMapping
    public String main(){
        return "index";
    }

    @PostMapping
    //public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
    public String uploadFile(@RequestParam("file") MultipartFile file){
        /*redirectAttributes.addFlashAttribute("message", "You successfully uploaded" + file.getOriginalFilename());
        return "redirect:/";*/
        long startTime = System.nanoTime();
        /**시간 측정 시작**/
        String originalName = file.getOriginalFilename();
        //log.info("originalName:" + originalName);
        String fileName = originalName.substring(originalName.lastIndexOf("\\")+1);
        log.info("fileName: " + fileName);  //fileName == originalName
        //makeFolder(fileName);

        String uuid = UUID.randomUUID().toString();
        String saveName = uploadPath + File.separator + uuid + "_" + fileName;
        //Path savePath = Paths.get(saveName);

        try {
            //file.transferTo(savePath);

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(file.getOriginalFilename())
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .build());
            /**시간 측정 끝**/
            long endTime = System.nanoTime();
            System.out.println("ElapsedTime " + (endTime - startTime));
        } catch (ErrorResponseException e) {
            throw new RuntimeException(e);
        } catch (InsufficientDataException e) {
            throw new RuntimeException(e);
        } catch (InternalException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (ServerException e) {
            throw new RuntimeException(e);
        } catch (XmlParserException e) {
            throw new RuntimeException(e);
        }
        /*for(MultipartFile file:files){

        }*/

        return "index";
    }

    private void makeFolder(String fileName){
        File uploadPathFolder = new File(uploadPath, fileName);
        if(uploadPathFolder.exists() == false)
            uploadPathFolder.mkdirs();
    }

}
