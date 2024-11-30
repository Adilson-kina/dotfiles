if status is-interactive
    # Commands to run in interactive sessions can go here
end

function y
	set tmp (mktemp -t "yazi-cwd.XXXXXX")
	yazi $argv --cwd-file="$tmp"
	if set cwd (command cat -- "$tmp"); and [ -n "$cwd" ]; and [ "$cwd" != "$PWD" ]
		builtin cd -- "$cwd"
	end
	rm -f -- "$tmp"
end

# Alias
alias "exa"=eza
alias xampanel="xhost +SI:localuser:root && sudo /opt/lampp/manager-linux-x64.run"
alias emulicious="java -jar $HOME/Downloads/Emulicious/Emulicious.jar"
alias mupdf="mupdf -I"
alias netbeans="netbeans --jdkhome /home/adilson/.asdf/installs/java/openjdk-17"

export TERM=xterm-256color

# Start Hyprland
if test -z "$DISPLAY" -a (tty) = "/dev/tty1"
    exec Hyprland
else
    echo "Not starting Hyprland: DISPLAY=$DISPLAY, TTY=(tty)" >> /tmp/hyprland_log.txt
end

# Fastfetch
fastfetch

# Export variable paths
source "$HOME/.asdf/asdf.fish"

set -gx PATH /opt/android-sdk/platform-tools $PATH
set -gx PATH /opt/lampp/bin/ $PATH

# Created by `pipx` on 2024-10-27 19:43:50
set -gx PATH $PATH /home/adilson/.local/bin

