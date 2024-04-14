package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Rating;
import java.util.List;
import java.util.Optional;

public interface RatingDao {
    Optional<List<Rating>> getAllRatings(long serviceid);
    Optional<Rating> findById(long id);
    Rating create(long serviceid, long userid, int rating, String comment);
    double getRatingsAvg(long serviceid);

}
