package com.epam.izh.rd.online.repository;

import com.epam.izh.rd.online.service.SimpleRegExpService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class SimpleFileRepository implements FileRepository {
    private long countFiles;
    private long countDirs;
    public static final String MESSAGE_BAD_PARAMS = "Param or params are bad or null";
    /**
     * Метод рекурсивно подсчитывает количество файлов в директории
     *
     * @param path путь до директори
     * @return файлов, в том числе скрытых
     */
    @Override
    public long countFilesInDirectory(String path) {
        if (path != null) {
            File rootDir = new File(path);
            if (rootDir.exists()) {
                File[] filesInDirectory = rootDir.listFiles();
                try {
                    for (File file : filesInDirectory) {
                        if (file.isDirectory()) {
                            countFilesInDirectory(file.getPath());
                        } else {
                            countFiles++;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                ClassLoader classLoader = getClass().getClassLoader();
                rootDir = new File(classLoader.getResource(path).getFile());
                if (rootDir.exists()) {
                    File[] filesInDirectory = rootDir.listFiles();
                    try {
                        for (File file : filesInDirectory) {
                            if (file.isDirectory()) {
                                countFilesInDirectory(file.getPath());
                            } else {
                                countFiles++;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else System.out.println(MESSAGE_BAD_PARAMS);
        return countFiles;
    }

    /**
     * Метод рекурсивно подсчитывает количество папок в директории, считая корень
     *
     * @param path путь до директории
     * @return число папок
     */
    @Override
    public long countDirsInDirectory(String path) {
        if (path != null) {
            File rootDir = new File(path);
            if (rootDir.exists()) {
                File[] filesInDirectory = rootDir.listFiles();
                try {
                    for (File file : filesInDirectory) {
                        if (file.isDirectory()) {
                            if (countDirs == 0) {
                                countDirs = 2;
                            } else {
                                countDirs++;
                            }
                            countDirsInDirectory(file.getPath());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                ClassLoader classLoader = getClass().getClassLoader();
                rootDir = new File(classLoader.getResource(path).getFile());
                if (rootDir.exists()) {
                    File[] filesInDirectory = rootDir.listFiles();
                    try {
                        for (File file : filesInDirectory) {
                            if (file.isDirectory()) {
                                if (countDirs == 0) {
                                    countDirs = 2;
                                } else {
                                    countDirs++;
                                }
                                countDirsInDirectory(file.getPath());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else System.out.println(MESSAGE_BAD_PARAMS);
        return countDirs;
    }

    /**
     * Метод копирует все файлы с расширением .txt
     *
     * @param from путь откуда
     * @param to   путь куда
     */
    @Override
    public void copyTXTFiles(String from, String to) {
        if (from != null && to != null) {
            File dirFrom = new File(from);
            File dirTo = new File(to);
            if (dirFrom != null && dirTo != null) {
                if (!dirTo.exists()) {
                    dirTo.mkdir();
                }
                for (File file : dirFrom.listFiles()) {
                    if (file.isFile() && file.getName().matches(".*\\.txt$")) {
                        try {
                            Files.copy(file.toPath(), Paths.get(dirTo.getPath() + "\\" + file.getName()), StandardCopyOption.REPLACE_EXISTING);
                            System.out.println(file.getName() + " " + "were copied into" + " " + dirTo.getPath());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (dirTo.listFiles().length == 0) System.out.println("No files were copied");
            }
        } else System.out.println(MESSAGE_BAD_PARAMS);

    }

    /**
     * Метод создает файл на диске с расширением txt
     *
     * @param path путь до нового файла
     * @param name имя файла
     * @return был ли создан файл
     */
    @Override
    public boolean createFile(String path, String name) {
        File file = null;
        if (path != null && name != null) {
            ClassLoader classLoader = getClass().getClassLoader();
            Path rootPath = Paths.get(new File("").getAbsolutePath());
            File newDirectory = new File(rootPath + "\\target\\classes\\" + path);
            if (!newDirectory.exists()) {
                newDirectory.mkdir();
            }
            file = new File(newDirectory.getPath() + "\\" + name);
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else System.out.println(MESSAGE_BAD_PARAMS);
        return file.exists();
    }

    /**
     * Метод считывает тело файла .txt из папки src/main/resources
     *
     * @param fileName имя файла
     * @return контент
     */
    @Override
    public String readFileFromResources(String fileName) {
        StringBuilder content = new StringBuilder("");
        if (fileName != null) {
            File rootDir = new File("src\\main\\resources");
                for (File elem : rootDir.listFiles()) {
                    if (elem.getName().equals(fileName)) {
                        try {
                            List<String> lines = Files.readAllLines(elem.toPath());
                            for (String string : lines) {
                                content.append(string);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        } else System.out.println(MESSAGE_BAD_PARAMS);
        return content.toString();
    }
}
