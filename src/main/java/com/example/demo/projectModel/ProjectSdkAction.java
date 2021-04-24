package com.example.demo.projectModel;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

public class ProjectSdkAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull final AnActionEvent event) {
        Project project = event.getProject();
        if (project != null) {
            Sdk sdk = ProjectRootManager.getInstance(project).getProjectSdk();
            if (sdk != null) {
                String projectSDKName = sdk.getName();
                String newProjectSdkName = "New Sdk Name";
                ProjectRootManager.getInstance(project).setProjectSdkName(newProjectSdkName, sdk.getSdkType().getName());
                Messages.showInfoMessage(projectSDKName + " has changed to " + newProjectSdkName, "Project Sdk Info");
            }
        }
    }

    @Override
    public void update(@NotNull final AnActionEvent event) {
        Project project = event.getProject();
        if (project != null) {
            Sdk sdk = ProjectRootManager.getInstance(project).getProjectSdk();
            event.getPresentation().setEnabledAndVisible(sdk != null);
        }
    }

}
