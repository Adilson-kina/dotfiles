#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#define BUFFER_SIZE 128

// Function to get the active workspace name
void get_active_workspace(char *buffer, size_t size) {
    FILE *fp;
    fp = popen("hyprctl activeworkspace -j | jq -r .name", "r");
    if (fp == NULL) {
        perror("Failed to run command");
        exit(1);
    }

    if (fgets(buffer, size, fp) != NULL) {
        // Strip newline character
        buffer[strcspn(buffer, "\n")] = 0;
    }

    pclose(fp);
}

int main() {
    char holder[BUFFER_SIZE] = "";
    char query[BUFFER_SIZE];

    while (1) {
        get_active_workspace(query, BUFFER_SIZE);
        if (strcmp(holder, query) != 0) {
            strcpy(holder, query);
            printf("%s\n", holder);
            fflush(stdout);
        }
        usleep(100000); // 100ms sleep to reduce CPU usage
    }

    return 0;
}

