package br.com.blogsanapi.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.blogsanapi.model.publication.Publication;

public interface PublicationRepository extends JpaRepository<Publication, Long>{

	@Query("SELECT p FROM Publication p WHERE DATE(p.date) = :date")
	Page<Publication> findAllByDate(Pageable pageable, LocalDate date);

	void deleteByUserIdAndId(Long userId, Long publiId);

	Page<Publication> findAllByUserId(Pageable pageable, Long id);
}
