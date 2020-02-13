package atdd.service.impl;

import atdd.dto.Station;
import atdd.repository.StationRepository;
import atdd.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class StationServiceImpl implements StationService {

    @Resource
    private StationRepository stationRepository;

    @Override
    public Station createStation(Station station) {
        return stationRepository.save(station);
    }

    @Override
    public List<Station> getStation() {
        return stationRepository.findAll();
    }

    @Override
    public Station detailStation(String stationName) {
        return stationRepository.findByName(stationName);
    }

    @Override
    public Long deleteStation(String stationName) {

        Station station =  stationRepository.findByName(stationName);
        stationRepository.deleteById(station.getId());

        return station.getId();

    }
}
