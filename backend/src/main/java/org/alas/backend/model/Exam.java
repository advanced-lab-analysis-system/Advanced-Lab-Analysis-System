package org.alas.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Exam {

    @Id
    private String exam_id;
    private String batch_id;
    private String exam_name;
    private String exam_type;
    private String subject;
    private String exam_date;
    private String exam_start_time;
    private String exam_end_time;
    private String conductedBy;
    private String class_and_section;
    private String exam_status;

    public String getExam_id() {
        return exam_id;
    }

    public void setExam_id(String exam_id) {
        this.exam_id = exam_id;
    }

    public String getBatch_id() {
        return batch_id;
    }

    public void setBatch_id(String batch_id) {
        this.batch_id = batch_id;
    }

    public String getExam_name() {
        return exam_name;
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
    }

    public String getExam_type() {
        return exam_type;
    }

    public void setExam_type(String exam_type) {
        this.exam_type = exam_type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getExam_date() {
        return exam_date;
    }

    public void setExam_date(String exam_date) {
        this.exam_date = exam_date;
    }

    public String getExam_start_time() {
        return exam_start_time;
    }

    public void setExam_start_time(String exam_start_time) {
        this.exam_start_time = exam_start_time;
    }

    public String getExam_end_time() {
        return exam_end_time;
    }

    public void setExam_end_time(String exam_end_time) {
        this.exam_end_time = exam_end_time;
    }

    public String getConductedBy() {
        return conductedBy;
    }

    public void setConductedBy(String conductedBy) {
        this.conductedBy = conductedBy;
    }

    public String getClass_and_section() {
        return class_and_section;
    }

    public void setClass_and_section(String class_and_section) {
        this.class_and_section = class_and_section;
    }

    public String getExam_status() {
        return exam_status;
    }

    public void setExam_status(String exam_status) {
        this.exam_status = exam_status;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "exam_id='" + exam_id + '\'' +
                ", batch_id='" + batch_id + '\'' +
                ", exam_name='" + exam_name + '\'' +
                ", exam_type='" + exam_type + '\'' +
                ", subject='" + subject + '\'' +
                ", exam_date='" + exam_date + '\'' +
                ", exam_start_time='" + exam_start_time + '\'' +
                ", exam_end_time='" + exam_end_time + '\'' +
                ", conductedBy='" + conductedBy + '\'' +
                ", class_and_section='" + class_and_section + '\'' +
                ", exam_status='" + exam_status + '\'' +
                '}';
    }
}
