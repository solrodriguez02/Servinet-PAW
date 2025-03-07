package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Rating;
import java.util.List;
import java.util.Optional;

public interface RatingDao {
    List<Rating> getAllRatings(long serviceid, int page);
    Optional<Rating> findById(long id);
    Rating create(long serviceid, long userid, int rating, String comment);
    double getRatingsAvg(long serviceid);
    int getRatingsCount(long serviceid);
    Optional<Rating> hasAlreadyRated(long userid, long serviceid);
    void edit(long ratingid, int rating, String comment);

}
