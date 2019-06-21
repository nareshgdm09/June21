package com.task.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.task.entity.Player;
import com.task.exception.PlayerDataAccessException;

@Service
public class PlayerWebServiceImpl implements PlayerWebService {
	private static final Logger logger = LoggerFactory.getLogger(PlayerWebServiceImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	public List<Player> findCustomPayer(Player playerobj) throws PlayerDataAccessException {
		logger.debug("inside of findCustomPayer(Player playerobj) methos :");

		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Player> query = builder.createQuery(Player.class);
			Root<Player> player = query.from(Player.class);

			List<Predicate> predicates = new ArrayList<Predicate>();

			// Adding predicates in case of parameter not being null
			if (playerobj.getId() != 0) {
				predicates.add(builder.equal(player.get("id"), playerobj.getId()));
			}
			if (playerobj.getName() != null) {
				predicates.add(builder.equal(player.get("name"), playerobj.getName()));
			}

			query.select(player).where(predicates.toArray(new Predicate[] {}));

			return entityManager.createQuery(query).getResultList();
		} catch (Exception e) {
			logger.error("Error Occured at findCustomPayer(Player playerobj)");
			throw new PlayerDataAccessException("findCustomPayer method exception");
		}
	}
}
