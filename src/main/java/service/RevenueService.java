package service;

import domain.Revenue;

import static util.CommonUtil.delay;

public class RevenueService {

    public Revenue getRevenue(Long movieId) {
        delay(1000);
        return Revenue.builder()
                .movieId(movieId)
                .budget(1000000)
                .boxOffice(5000000)
                .build();
    }
}
