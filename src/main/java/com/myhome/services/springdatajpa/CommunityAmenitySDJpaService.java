package com.myhome.services.springdatajpa;

import com.myhome.domain.Community;
import com.myhome.domain.CommunityAmenity;
import com.myhome.repositories.CommunityAmenityRepository;
import com.myhome.services.CommunityAmenityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommunityAmenitySDJpaService implements CommunityAmenityService {

  private final CommunityAmenityRepository communityAmenityRepository;

  @Override
  public Optional<CommunityAmenity> getCommunityAmenityDetails(String amenityId) {
    return communityAmenityRepository.findByAmenityId(amenityId);
  }

  @Override
  public boolean deleteAmenity(String amenityId) {
    return communityAmenityRepository.findByAmenityIdWithCommunity(amenityId)
        .map(amenity -> {
          Community community = amenity.getCommunity();
          community.getAmenities().remove(amenity);
          communityAmenityRepository.delete(amenity);
          return true;
        })
        .orElse(false);
  }

}
