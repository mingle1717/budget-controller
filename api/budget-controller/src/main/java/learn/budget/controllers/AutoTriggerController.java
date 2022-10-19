//package learn.budget.controllers;
//
//import learn.budget.domain.AutoTriggerService;
//import learn.budget.domain.Result;
//import learn.budget.models.AutoTrigger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
////@RestController
////@CrossOrigin
////@RequestMapping("/api/auto")
//public class AutoTriggerController {
//
//    @Autowired
//    AutoTriggerService service;
//
//    @GetMapping("/{username}")
//    public List<AutoTrigger> viewAllAutoTrigger(@PathVariable String username){
//        return service.viewTriggersByUser(username);
//    }
//
//    @PostMapping
//    public ResponseEntity<Object> addTrigger(@RequestBody AutoTrigger autoTrigger){
//        Result<AutoTrigger> result = service.addAutoTrigger(autoTrigger);
//        if(result.isSuccess()){
//            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
//        }
//        return ErrorResponse.build(result);
//    }
//
//    @PutMapping("/{auto_Id}")
//    public ResponseEntity<Object> updateAutoTrigger(@RequestBody AutoTrigger autoTrigger){
//        Result<AutoTrigger> result = service.update(autoTrigger);
//        if(result.isSuccess()){
//            return new ResponseEntity<>(result.getPayload(), HttpStatus.NO_CONTENT);
//        }
//        return ErrorResponse.build(result);
//        //Test comment to see if updated to Git
//    }
//}
