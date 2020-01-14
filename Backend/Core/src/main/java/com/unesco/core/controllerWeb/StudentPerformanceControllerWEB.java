package com.unesco.core.controllerWeb;

import com.unesco.core.controller.StudentPerformanceController;
import com.unesco.core.dto.additional.ResponseStatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Konstantinov V.V. on 05.01.2020
 */
@CrossOrigin
@RestController
@RequestMapping("api/performance")
public class StudentPerformanceControllerWEB {

    @Autowired
    private StudentPerformanceController studentPerformanceController;

    @RequestMapping(method = RequestMethod.POST, value = "/points")
    public ResponseStatusDTO getPoints(@RequestParam("userId") long userId) {
        return studentPerformanceController.getPoints(userId);
    }
}
