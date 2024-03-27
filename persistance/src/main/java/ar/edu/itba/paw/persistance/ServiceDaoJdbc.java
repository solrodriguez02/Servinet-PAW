package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.Service;
import ar.edu.itba.paw.services.ServiceDao;
import org.springframework.stereotype.Repository;

@Repository
public class ServiceDaoJdbc implements ServiceDao {
    @Override
    public Service findById(long id) {

        return new Service(id, "Nombre del servicio", "Breve descripcion del servicio prestado", "Ubicacion", "Categoria");
    }
}
