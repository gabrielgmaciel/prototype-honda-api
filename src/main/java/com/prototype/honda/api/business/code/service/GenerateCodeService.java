package com.prototype.honda.api.business.code.service;

import com.prototype.honda.api.business.code.model.GenerateCodeModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GenerateCodeService {

    private static final String SEQUENCE_FIELD = "sequencial";
    private static final String YEAR_SEQUENTIAL_FIELD = "yearSequential";
    private static final int INITIAL_SEQUENCE = 1000;

    private final MongoTemplate mongoTemplate;

    public String generateProposalCode() {
        long seq = getNextAvailableNumber();
        String date = String.valueOf(LocalDate.now().getYear());
        return String.format("PRO" + "-%s-%06d", date, seq);
    }

    private long getNextAvailableNumber() {
        int currentYear = LocalDate.now().getYear();

        Query query = new Query(Criteria.where("_id"));
        GenerateCodeModel codeModel = mongoTemplate.findOne(query, GenerateCodeModel.class);

        if (codeModel != null && codeModel.getYearSequential() != currentYear) {
            Update updateReset = new Update()
                    .set(SEQUENCE_FIELD, INITIAL_SEQUENCE)
                    .set(YEAR_SEQUENTIAL_FIELD, currentYear);
            mongoTemplate.updateFirst(query, updateReset, GenerateCodeModel.class);
            return INITIAL_SEQUENCE;
        }

        Update update = new Update().inc(SEQUENCE_FIELD, 1);
        FindAndModifyOptions options = new FindAndModifyOptions()
                .returnNew(true)
                .upsert(true);

        GenerateCodeModel result = Objects.requireNonNull(mongoTemplate.findAndModify(query, update, options, GenerateCodeModel.class));

        if (result.getYearSequential() == null || result.getYearSequential() == 0) {
            Update updateYear = new Update()
                    .set(YEAR_SEQUENTIAL_FIELD, currentYear)
                    .set(SEQUENCE_FIELD, INITIAL_SEQUENCE);
            mongoTemplate.updateFirst(query, updateYear, GenerateCodeModel.class);
            return INITIAL_SEQUENCE;
        }

        return result.getSequencial();
    }
}
