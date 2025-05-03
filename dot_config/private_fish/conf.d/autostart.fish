
######################
### SOME AUTOSTART ###
######################

# Start Hyprland
# Check if DISPLAY is not set AND we are on tty1
if not set -q DISPLAY; and tty | string match -q "/dev/tty1"
    exec Hyprland
# Optional: Handle other TTYs or cases where DISPLAY is set but Hyprland didn't start
# You might want to add checks here if needed.
else if tty | string match -q "/dev/tty" # Still unlikely, but using string match
    echo "Not starting Hyprland: DISPLAY=$DISPLAY, TTY=(tty)"
else if tty | string match -q "/dev/pts/*"
    # Do nothing or something else for pseudoterminals
    :
end

fastfetch
