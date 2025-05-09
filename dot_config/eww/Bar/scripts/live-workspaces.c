#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <jansson.h>

#define BUFFER_SIZE 8192
#define MAX_WORKSPACES 256 // Define a maximum number of workspaces

// Comparison function for qsort
int compare_integers(const void *a, const void *b) {
    long long int_a = *((long long*)a);
    long long int_b = *((long long*)b);

    if (int_a < int_b) return -1;
    if (int_a > int_b) return 1;
    return 0;
}

// Run hyprctl -j workspaces and get output
int run_hyprctl(char *outbuf, size_t outlen) {
    FILE *fp = popen("hyprctl -j workspaces", "r");
    if (!fp) {
        perror("popen");
        return -1;
    }

    size_t total = 0;
    // Read in chunks to avoid buffer overflow with fgets if a single line is too long
    // although with hyprctl output this is unlikely for BUFFER_SIZE
    while (total < outlen - 1) {
        if (fgets(outbuf + total, outlen - total, fp) == NULL) {
            if (feof(fp)) { // End of file
                break;
            } else { // Error
                perror("fgets");
                pclose(fp);
                return -1;
            }
        }
        total = strlen(outbuf); // Recalculate total length after fgets
    }
    outbuf[total] = '\0'; // Ensure null termination

    pclose(fp);
    return 0;
}

// Parse workspace IDs and sort them
void extract_and_sort_workspace_ids(const char *jsonstr, char *output, size_t outlen) {
    json_error_t error;
    json_t *root = json_loads(jsonstr, 0, &error);
    if (!root || !json_is_array(root)) {
        // fprintf(stderr, "Invalid JSON or not an array: %s\n", error.text);
        // Instead of printing to stderr, we can make the output reflect the error or be empty
        snprintf(output, outlen, "[ ]"); // Default to empty list on error
        if (root) json_decref(root);
        return;
    }

    long long ids[MAX_WORKSPACES];
    int count = 0;
    size_t index;
    json_t *value;

    json_array_foreach(root, index, value) {
        if (count >= MAX_WORKSPACES) {
            // fprintf(stderr, "Exceeded maximum workspace limit of %d\n", MAX_WORKSPACES);
            break;
        }
        json_t *id_json = json_object_get(value, "id");
        if (json_is_integer(id_json)) {
            long long id_val = json_integer_value(id_json);
            if (id_val >= 0) { // Considering workspaces can be 0 or positive
                ids[count++] = id_val;
            }
        }
    }
    json_decref(root);

    // Sort the IDs
    qsort(ids, count, sizeof(long long), compare_integers);

    // Build the output string
    char temp_str[BUFFER_SIZE] = "[ ";
    for (int i = 0; i < count; i++) {
        char buf[32]; // Sufficient for long long and ", "
        snprintf(buf, sizeof(buf), "%lld", ids[i]);
        strncat(temp_str, buf, sizeof(temp_str) - strlen(temp_str) - 1);
        if (i < count - 1) {
            strncat(temp_str, ", ", sizeof(temp_str) - strlen(temp_str) - 1);
        }
    }

    if (count > 0) {
         strncat(temp_str, " ]", sizeof(temp_str) - strlen(temp_str) - 1);
    } else {
         strncat(temp_str, "]", sizeof(temp_str) - strlen(temp_str) - 1); // For empty list "[ ]"
    }


    strncpy(output, temp_str, outlen -1);
    output[outlen -1] = '\0'; // Ensure null termination
}

int main() {
    char last_result[BUFFER_SIZE] = "";
    char raw_output[BUFFER_SIZE];
    char parsed_sorted[BUFFER_SIZE];

    while (1) {
        if (run_hyprctl(raw_output, sizeof(raw_output)) == 0) {
            // Ensure raw_output is null-terminated before passing to jansson
            // This is generally handled by run_hyprctl now.
            extract_and_sort_workspace_ids(raw_output, parsed_sorted, sizeof(parsed_sorted));
            if (strcmp(last_result, parsed_sorted) != 0) {
                strcpy(last_result, parsed_sorted);
                printf("%s\n", parsed_sorted);
                fflush(stdout);
            }
        } else {
            // Optionally, handle error from run_hyprctl, e.g., print a message or exit
            // For now, it will just retry.
            // fprintf(stderr, "Failed to run hyprctl\n");
        }

        usleep(200000); // 200ms delay
    }

    return 0;
}
