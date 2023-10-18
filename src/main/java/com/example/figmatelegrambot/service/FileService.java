package com.example.figmatelegrambot.service;

import com.example.figmatelegrambot.model.figma.entity.FigmaFile;
import com.example.figmatelegrambot.repository.FileRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    public FigmaFile saveFigmaFile(FigmaFile file) {
        return fileRepository.save(file);
    }
}
