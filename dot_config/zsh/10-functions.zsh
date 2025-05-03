zsh_add_path(){
  # Get the directory string from the first argument ($1)
  local dir="$1"

  # Check if the directory string is already in PATH
  # We pad PATH with colons to easily check for the string surrounded by colons
  case ":$PATH:" in
    *":$dir:"*)
      # String is already in PATH, do nothing
      ;;
    *)
      # String is not in PATH, add it and export
      export PATH="$PATH:$dir"
      # Optional: You can add a message to confirm it was added
      # echo "'$dir' added to PATH"
      ;;
  esac
}

