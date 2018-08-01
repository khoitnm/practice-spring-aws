package org.tnmk.practicespringaws.hotelview.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.tnmk.practicespringaws.hotelview.model.HotelView;

import java.util.Optional;

public interface HotelViewRepository extends JpaRepository<HotelView, Integer> {
    Optional<HotelView> findOneByAccountUuidAndHotelViewId(String accountUuid, String hotelViewId);
}
