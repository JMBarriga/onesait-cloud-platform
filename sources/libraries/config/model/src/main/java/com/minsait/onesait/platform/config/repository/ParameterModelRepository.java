/**
 * Copyright minsait by Indra Sistemas, S.A.
 * 2013-2018 SPAIN
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.minsait.onesait.platform.config.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.minsait.onesait.platform.config.model.Model;
import com.minsait.onesait.platform.config.model.ParameterModel;

public interface ParameterModelRepository extends JpaRepository<ParameterModel, Long> {

	ParameterModel findById(String id);

	List<ParameterModel> findByIdentification(String identification);

	List<ParameterModel> findByIdentificationContaining(String identification);

	List<ParameterModel> findAllByOrderByIdentificationAsc();

	List<ParameterModel> findByIdentificationLike(String identification);

	ParameterModel findByIdentificationAndModel(String identification, Model model);

	@Query("select o from ParameterModel as o where o.model = :model")
	List<ParameterModel> findAllByModel(@Param("model") Model model);

}