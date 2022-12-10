package dz.me.dashboard.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dz.me.dashboard.entities.Ticket;

/**
 *
 * @author Tarek Mekriche
 */
public interface TicketRepository extends JpaRepository<Ticket, Long> {
        // pour impression ticket
        @Query(value = "SELECT 1+nvl(max(cast(ticket AS INT)),0)  maxTicket FROM ticket WHERE id_service = ? "
                        + "AND DATE(date_edition)=DATE(NOW()) ", nativeQuery = true)
        public long findLastTicketEditer(String IdService);

        @Query(value = "INSERT INTO ticket (date_edition,is_transferred,pseudo_ticket,ticket,id_service) VALUES (DATE(NOW()),?1,?2,?3,?4)", nativeQuery = true)
        public Ticket imprimerTicketSuivant(boolean isTransferred, String pseudoTicket, String ticket,
                        String idService);

        // pour appel ticket
        /*
         * @Query(value =
         * "SELECT nvl(min(cast(ticket AS INT)),0)   FROM ticket WHERE id_service = ? "
         * + "AND DATE(date_edition)=DATE(NOW()) AND date_appel IS  NULL  ", nativeQuery
         * = true)
         * public int minTicketAppeler(String IdService);
         */

        @Query(value = "SELECT nvl(cast(ticket AS INT),0)   FROM ticket WHERE id_service = ? "
                        + "AND DATE(date_edition)=DATE(NOW()) AND date_appel IS  NULL ORDER BY prioritaire desc ,nvl(cast(ticket AS INT),0) LIMIT 1  ", nativeQuery = true)
        public int minTicketAppeler(String IdService);

        @Query(value = "SELECT * FROM ticket WHERE id_service = ?1 and ticket = ?2 "
                        + "AND DATE(date_edition)=DATE(NOW()) ", nativeQuery = true)
        public Ticket findByTicketAndServiceAndSysDate(String IdService, String minTicket);

        @Query(value = " SELECT nvl(max(cast(ticket AS INT)),0)   FROM ticket WHERE id_service = ? AND DATE(date_edition)=DATE(NOW()) AND date_appel IS not NULL ", nativeQuery = true)
        public int dernierTicketAppeleService(String IdService);

        @Query(value = " SELECT *  FROM ticket WHERE id_service IN ?1 AND DATE(date_edition)=DATE(NOW()) AND date_appel IS not NULL  order by date_appel DESC  LIMIT ?2 ", nativeQuery = true)
        public List<Ticket> dernierTicketAppeleSeviceCommun(List<String> IdService, int limit);

        @Query(value = " SELECT *  FROM ticket WHERE id_service IN ?1 AND DATE(date_edition)=DATE(NOW()) AND date_appel IS not NULL  order by date_appel DESC  LIMIT 1 ", nativeQuery = true)
        public Ticket dernierTicketAppeleSeviceCommun(List<String> IdService);

        @Query(value = " SELECT COUNT(*)   FROM ticket WHERE id_service = ? AND DATE(date_edition)=DATE(NOW()) AND date_appel IS  NULL ", nativeQuery = true)
        public int nombreTicketInstanceService(String IdService);

        @Query(value = " SELECT COUNT(*)   FROM ticket WHERE id_service in ?1 AND DATE(date_edition)=DATE(NOW()) AND date_appel IS  NULL ", nativeQuery = true)
        public int nombreTicketInstanceServiceCommun(List<String> IdService);

        @Query(value = " SELECT nvl(round(cast(avg(TIME_TO_SEC(TIMEDIFF(nvl(date_appel,DATE(NOW())),date_edition))) as INT ) /60),0)  as differencecket  FROM ticket WHERE id_service=? and date_format(date_edition, '%Y-%m-%d')= date_format(DATE(NOW()), '%Y-%m-%d') AND date_appel IS NOT null", nativeQuery = true)
        public int delaisAttenteMoyen(String IdService);

        @Query(value = " SELECT nvl(round(cast(avg(TIME_TO_SEC(TIMEDIFF(nvl(date_traitement,DATE(NOW())),nvl(date_appel,date_edition)))) as INT ) /60) ,0)as differencecket from ticket WHERE id_service=?  and date_format(date_edition, '%Y-%m-%d')= date_format(DATE(NOW()), '%Y-%m-%d')", nativeQuery = true)
        public int delaisTraitementMoyen(String IdService);

        @Query(value = "delete from Ticket", nativeQuery = true)
        public void deleteAll();

        @Query(value = "delete from ticket WHERE DATE(date_edition)<>DATE(NOW())", nativeQuery = true)
        public void deleteOld();

        public Optional<Ticket> findByIdPseudoTicket(UUID id);

        @Query(value = "SELECT nvl(CONCAT(MAX(ticket),s. service_lettre ),0)"
                        + " FROM ticket t ,service s WHERE s.id=t.id_service AND  date_traitement IS NULL AND guichet_id=?1"
                        + " AND user_appel_id=?2 AND t.id_service =?3 " +
                        "AND DATE(date_edition)=DATE(NOW())  ", nativeQuery = true)
        public String lastServiceByGuichetUserIdService(String idGuichet, String IdUserAppel, String IdService);

        @Query(value = "SELECT COUNT(*) FROM ticket t WHERE  date_traitement IS not NULL AND user_trait_id=?1 AND DATE(date_edition)=DATE(NOW()) ", nativeQuery = true)
        public int nbrTicketByUser(String IdUserTrait);

        @Query(value = "SELECT * FROM ticket WHERE  DATE(date_appel)=DATE(NOW())     and guichet_id =?1  ORDER BY ticket desc LIMIT 1", nativeQuery = true)
        public Optional<Ticket> lastTicketByGuichet(String idGuichet);

}
