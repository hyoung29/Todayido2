////package com.metamong.todayido.controller;
////
////import com.metamong.todayido.dto.StoreDto;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.stereotype.Controller;
////import org.springframework.stereotype.Service;
////import java.util.List;
////import java.util.stream.Collectors;
////
////@Controller
////public class SearchController {
////
////    private final StoreDto storeDto; // 가게 정보를 다루는 Repository
////
////    @Autowired
////    public SearchService(StoreDto stoDto) {
////        this.storeDto = stoDto;
////    }
////
////    public List<StoreDto> searchStores(String keyword) {
////        // 여기서는 가게 이름 또는 번호가 검색어에 포함되는 경우를 가정합니다.
////        // 실제로는 검색을 어떻게 정의할지에 따라 쿼리가 달라질 수 있습니다.
////        List<StoreDto> Store = storeDto.getStore_num(); // 데이터베이스에서 모든 가게 정보 가져오기
////
////        // 검색어가 포함된 가게 정보 필터링
////        List<StoreDto> filteredStores = Store.stream()
////                .filter(store -> store.getStore_name().toLowerCase().contains(keyword.toLowerCase()))
////                .collect(Collectors.toList());
////
////        return filteredStores;
////    }
////}
////
//package com.metamong.todayido.controller;
//
//import com.metamong.todayido.service.SearchService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//@Controller
//@Slf4j
//public class SearchController {
//    @Autowired
//    private SearchService seaServ;
//
//    @GetMapping("content")
//    public ModelAndView content(String keyword){
//        log.info("content()");
//        ModelAndView mv = seaServ.searchByKeyword(String keyword);
//        return mv;
//    }
//}