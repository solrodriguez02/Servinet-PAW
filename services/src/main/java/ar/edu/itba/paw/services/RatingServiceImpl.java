package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service("RatingServiceImpl")
public class RatingServiceImpl implements RatingService {

    private final RatingDao ratingDao;

    @Autowired
    public RatingServiceImpl(final RatingDao ratingDao) {
        this.ratingDao = ratingDao;
    }

    @Override
    public List<Rating> getAllRatings(long serviceid) {
        List<Rating> ratings;
        if(ratingDao.getAllRatings(serviceid).isPresent()) {
            ratings = ratingDao.getAllRatings(serviceid).get();
            if(ratings.isEmpty()) ratings = null;
        } else ratings = null;
        return ratings;
    }

    @Override
    public Optional<Rating> findById(long id) {
        return ratingDao.findById(id);
    }

    @Override
    public Rating create(long serviceid, long userid, int rating, String comment) {
        return ratingDao.create(serviceid, userid, rating, comment);
    }

    @Override
    public double getRatingsAvg(long serviceid) {
        return getRatingsAvg(serviceid);
    }
}
