package br.com.kebos.service;

import br.com.kebos.model.Recommendation;
import br.com.kebos.model.User;

import java.util.List;

public interface PartnerService {

    List<User> listAllPartner();
    List<Recommendation> listRecomendations();
    Recommendation listByIdRecommendation(long id);
    Recommendation saveRecommendation(Recommendation recommendation);

}
