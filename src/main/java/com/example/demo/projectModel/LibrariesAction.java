package com.example.demo.projectModel;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.*;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class LibrariesAction extends AnAction {

    @Override
    public void update(@NotNull final AnActionEvent event) {
        Project project = event.getProject();
        if (project == null) {
            return;
        }
        Navigatable element = event.getData(CommonDataKeys.NAVIGATABLE);
        if (element instanceof PsiClass) {
            PsiFile psiFile = ((PsiClass) element).getContainingFile();
            if (psiFile == null) {
                return;
            }
            VirtualFile virtualFile = psiFile.getVirtualFile();
            if (virtualFile == null) {
                return;
            }
            event.getPresentation().setEnabledAndVisible(true);
        }
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        if (project == null) {
            return;
        }
        Navigatable element = event.getData(CommonDataKeys.NAVIGATABLE);
        if (element instanceof PsiClass) {
            PsiFile psiFile = ((PsiClass) element).getContainingFile();
            if (psiFile == null) {
                return;
            }
            VirtualFile virtualFile = psiFile.getVirtualFile();
            if (virtualFile == null) {
                return;
            }
            final ProjectFileIndex fileIndex = ProjectRootManager.getInstance(project).getFileIndex();
            StringBuilder jars = new StringBuilder();
            for (OrderEntry orderEntry : fileIndex.getOrderEntriesForFile(virtualFile)) {
                if (orderEntry instanceof LibraryOrderEntry) {
                    final LibraryOrderEntry libraryEntry = (LibraryOrderEntry) orderEntry;
                    final Library library = libraryEntry.getLibrary();
                    if (library == null) {
                        continue;
                    }
                    VirtualFile[] files = library.getFiles(OrderRootType.CLASSES);
                    if (files.length == 0) {
                        continue;
                    }
                    for (VirtualFile jar : files) {
                        jars.append(jar.getName()).append(", ");
                    }
                }
            }
            String fileAndLibs;
            if (jars.length() > 0) {
                fileAndLibs = virtualFile.getName() + ": " + jars.toString();
            } else {
                fileAndLibs = "None";
            }
            Messages.showInfoMessage("Libraries for file: " + fileAndLibs,
                    "Libraries Info");
        }
    }

}
