package com.unesco.core.services.dataService.plan.planService;

import com.unesco.core.dto.additional.FilterQueryDTO;
import com.unesco.core.dto.additional.ResponseStatusDTO;
import com.unesco.core.dto.enums.StatusTypes;
import com.unesco.core.dto.plan.PlanDTO;
import com.unesco.core.entities.plan.PlanEntity;
import com.unesco.core.repositories.plan.PlanRepository;
import com.unesco.core.services.mapperService.IMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlanDataService implements IPlanDataService {

    @Autowired
    private IMapperService mapperService;
    @Autowired
    private PlanRepository planRepository;

    public List<PlanDTO> getPage(FilterQueryDTO filter) {
        int rows = filter.getRows()>0? filter.getRows() : (int) planRepository.count();
        int start = rows>0 ? filter.getFirst()/rows: 1;
        List<PlanEntity> entitys = planRepository.findWithFilter(new PageRequest(start, rows == 0 ? 10 : rows), filter.getGlobalFilter());
        List<PlanDTO> result = new ArrayList<PlanDTO>();
        for (PlanEntity e: entitys) {
            result.add((PlanDTO) mapperService.toDto(e));
        }
        return result;
    }

    public List<PlanDTO> getAll()
    {
        List<PlanDTO> modelList = new ArrayList<>();
        Iterable<PlanEntity> entityList = planRepository.findAll();
        for (PlanEntity item: entityList) {
            PlanDTO model = (PlanDTO) mapperService.toDto(item);
            modelList.add(model);
        }
        return modelList;
    }

    public PlanDTO get(long id)
    {
        PlanEntity entity = planRepository.findOne(id);
        PlanDTO model = (PlanDTO) mapperService.toDto(entity);
        return model;
    }

    public ResponseStatusDTO<PlanDTO> delete(long id)
    {
        ResponseStatusDTO<PlanDTO> result = new ResponseStatusDTO<>(StatusTypes.OK);
        try {
            planRepository.delete(id);
        } catch (Exception e) {
            result.setStatus(StatusTypes.ERROR);
            if(e instanceof DataIntegrityViolationException)
                result.addErrors("Удаление не удалось. У объекта есть зависимости.");
            result.addErrors("Удаление не удалось");
            return result;
        }
        return result;
    }

    public ResponseStatusDTO<PlanDTO> save(PlanDTO plan)
    {
        PlanEntity entity = (PlanEntity) mapperService.toEntity(plan);
        ResponseStatusDTO<PlanDTO> result = new ResponseStatusDTO<>(StatusTypes.OK);
        try {
            entity = planRepository.save(entity);
        } catch (Exception e) {
            result.setStatus(StatusTypes.ERROR);
            result.addErrors(e.getMessage());
            return result;
        }
        result.setData((PlanDTO) mapperService.toDto(entity));
        return result;
    }
}
