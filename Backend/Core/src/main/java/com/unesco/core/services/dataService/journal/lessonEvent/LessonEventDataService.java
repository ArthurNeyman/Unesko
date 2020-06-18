package com.unesco.core.services.dataService.journal.lessonEvent;

import com.unesco.core.dto.additional.ResponseStatusDTO;
import com.unesco.core.dto.enums.StatusTypes;
import com.unesco.core.dto.journal.LessonEventDTO;
import com.unesco.core.dto.shedule.PairDTO;
import com.unesco.core.entities.journal.LessonEventEntity;
import com.unesco.core.entities.schedule.PairEntity;
import com.unesco.core.repositories.journal.LessonEventRepository;
import com.unesco.core.repositories.schedule.PairRepository;
import com.unesco.core.services.mapperService.IMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LessonEventDataService implements ILessonEventDataService {

    @Autowired
    private IMapperService mapperService;
    @Autowired
    private LessonEventRepository lessonEventRepository;

    @Autowired
    private PairRepository pairRepository;

    public List<LessonEventDTO> getAll()
    {
        List<LessonEventDTO> modelList = new ArrayList<>();
        Iterable<LessonEventEntity> entityList = lessonEventRepository.findAll();
        for (LessonEventEntity item: entityList) {
            LessonEventDTO model = (LessonEventDTO) mapperService.toDto(item);
            modelList.add(model);
        }
        return modelList;
    }

    public LessonEventDTO get(long id)
    {
        LessonEventEntity entity = lessonEventRepository.findOne(id);
        LessonEventDTO model = (LessonEventDTO) mapperService.toDto(entity);
        return model;
    }

    public List<LessonEventDTO> getByLesson(long lessonId)
    {
        List<LessonEventDTO> modelList = new ArrayList<>();
        Iterable<LessonEventEntity> entityList = lessonEventRepository.findByLessonEntityId(lessonId);
        for (LessonEventEntity item: entityList) {
            LessonEventDTO model = (LessonEventDTO) mapperService.toDto(item);
            modelList.add(model);
        }
        return modelList;
    }

    public ResponseStatusDTO<LessonEventDTO> delete(long id)
    {
        ResponseStatusDTO<LessonEventDTO> result = new ResponseStatusDTO<>(StatusTypes.OK);
        try {
            lessonEventRepository.delete(id);
        } catch (Exception e) {
            result.setStatus(StatusTypes.ERROR);
            if(e instanceof DataIntegrityViolationException)
                result.addErrors("Удаление не удалось. У объекта есть зависимости.");
            result.addErrors("Удаление не удалось");
            return result;
        }
        return result;
    }

    public ResponseStatusDTO<LessonEventDTO> save(LessonEventDTO lessonEvent)
    {
        LessonEventEntity entity = (LessonEventEntity) mapperService.toEntity(lessonEvent);
        ResponseStatusDTO<LessonEventDTO> result = new ResponseStatusDTO<>(StatusTypes.OK);
        try {
            Set<PairEntity> pairs = entity.getPairs();
            entity.setPairs(new HashSet<>());
            entity = lessonEventRepository.save(entity);
            entity.setPairs(pairs);
            entity = lessonEventRepository.save(entity);
        } catch (Exception e) {
            result.setStatus(StatusTypes.ERROR);
            result.addErrors(e.getMessage());
            return result;
        }
        result.setData((LessonEventDTO) mapperService.toDto(entity));
        return result;
    }

    public int getSumMaxValueBetweenDates(long lessonId, Date start, Date end){
        return (lessonEventRepository.getMaxPointValue(lessonId,start,end)!=null)?lessonEventRepository.getMaxPointValue(lessonId,start,end):0;
    }

    public int getSumMaxValue(long lessonId){
        return (lessonEventRepository.getMaxPointValue(lessonId)!=null)?lessonEventRepository.getMaxPointValue(lessonId):0;
    }

}
