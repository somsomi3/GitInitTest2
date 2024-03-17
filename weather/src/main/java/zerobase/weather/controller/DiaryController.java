package zerobase.weather.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import zerobase.weather.domain.Diary;
import zerobase.weather.service.DiaryService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.time.LocalDate;
import java.util.List;

@RestController
public class DiaryController {
    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }



    @Operation(summary = "일기 텍스트와 날씨를 이용해서 DB에 일기 저장", description="자기 스스로 일기를 한번 써보아요!")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema)),
            @ApiResponse(responseCode = "401", description = "인가 기능이 확인되지 않은 접근", content = @Content(schema = @Schema)),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema)),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생", content = @Content(schema = @Schema))
    })
    @PostMapping("/create/diary")
    void createDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,@RequestBody String text){
        diaryService.createDiary(date, text);
    }


    @Operation(summary = "선택한 날짜에 모든 일기 데이터를 가져옵니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema)),
            @ApiResponse(responseCode = "401", description = "인가 기능이 확인되지 않은 접근", content = @Content(schema = @Schema)),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema)),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생", content = @Content(schema = @Schema))
    })

    @GetMapping("/read/diary")
    List<Diary> readDiary(@RequestParam(value= "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)@Parameter(name = "date", description = "날짜형식: yyyy-MM-dd", example = "2023-10-03", required = true)LocalDate date){

        return diaryService.readDiary(date);
    }


    @Operation(summary ="선택한 기간중의 모든 일기 데이터를 가져옵니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema)),
            @ApiResponse(responseCode = "401", description = "인가 기능이 확인되지 않은 접근", content = @Content(schema = @Schema)),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema)),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생", content = @Content(schema = @Schema)),
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema)),
            @ApiResponse(responseCode = "401", description = "인가 기능이 확인되지 않은 접근", content = @Content(schema = @Schema)),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema)),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생", content = @Content(schema = @Schema))})

    @GetMapping("/read/diaries")

    List<Diary> readDiaries(@RequestParam (value= "startDate", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)@Parameter(name = "startDate", description = "날짜형식: yyyy-MM-dd", example = "2023-10-03", required = true) LocalDate startDate,
                            @RequestParam (value= "endDate", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)@Parameter(name = "endDate", description = "날짜형식: yyyy-MM-dd", example = "2023-10-23", required = true) LocalDate endDate){
        return  diaryService.readDiaries(startDate, endDate);
    }

    
    @PutMapping("/update/diary")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema)),
            @ApiResponse(responseCode = "401", description = "인가 기능이 확인되지 않은 접근", content = @Content(schema = @Schema)),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema)),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생", content = @Content(schema = @Schema))
    })
    void updateDiary(@RequestParam(value= "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Parameter(name = "date", description = "날짜형식: yyyy-MM-dd", example = "2023-10-03", required = true)LocalDate date, @RequestBody String text){
        diaryService.updateDiary(date, text);
    }


    @DeleteMapping("/delete/diary")//일기를 삭제하는 함수를 만들어 보자
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema)),
            @ApiResponse(responseCode = "401", description = "인가 기능이 확인되지 않은 접근", content = @Content(schema = @Schema)),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema)),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생", content = @Content(schema = @Schema))
    })
    void deleteDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date ){
        diaryService.deleteDiary(date);
    }
}
