package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.Categories;
import ar.edu.itba.paw.model.PricingTypes;
import ar.edu.itba.paw.model.Service;
import ar.edu.itba.paw.services.ServiceDao;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ServiceDaoJdbc implements ServiceDao {
    @Override
    public Optional<Service> findById(long id) {
        //TODO: Implementar
        return Optional.of(new Service(id, 1,"Nombre del servicio", "Breve descripcion del servicio prestado", Boolean.TRUE , "Callao 400", null, Categories.LIMPIEZA.getValue(), 60, PricingTypes.PER_TOTAL.getValue(), "$4000", Boolean.TRUE));
    }
}
