package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    @Autowired
    BlogRepository blogRepository2;

    @Autowired
    ImageRepository imageRepository2;

    public Image addImage(Integer blogId, String description, String dimensions) {
        // Add an image to the blog
        Blog blog = blogRepository2.findById(blogId).get();
        Image image = new Image(blog, description, dimensions);
        blog.getImageList().add(image);
        blogRepository2.save(blog);
        return image;
        // Here I am not explicitly adding the image in the image repository because of the cascading effect
    }

    public void deleteImage(Integer id) {
        // Delete an image by its id
        imageRepository2.deleteById(id);
    }

    public int countImagesInScreen(Integer id, String screenDimensions) {
        // Find the number of images of given dimensions that can fit in a screen having `screenDimensions`
        String[] scrArray = screenDimensions.split("X"); // A = Length   X    B = Breadth
        Image image = imageRepository2.findById(id).get();

        String imageDimensions = image.getDimensions();
        String[] imgArray = imageDimensions.split("X");

        int scrl = Integer.parseInt(scrArray[0]); // A --> integer
        int scrb = Integer.parseInt(scrArray[1]); // B --> integer

        int imgl = Integer.parseInt(imgArray[0]); // A --> integer
        int imgb = Integer.parseInt(imgArray[1]); // B --> integer

        return calculateNoOfImages(scrl, scrb, imgl, imgb);
    }

    private int calculateNoOfImages(int scrl, int scrb, int imgl, int imgb) {
        int lenC = scrl / imgl; //
        int lenB = scrb / imgb;
        return lenC * lenB;
    }
}
