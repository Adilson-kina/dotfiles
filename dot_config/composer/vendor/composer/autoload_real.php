<?php

// autoload_real.php @generated by Composer

class ComposerAutoloaderInitf35e8fd58eb377b73fdf83653dc88f05
{
    private static $loader;

    public static function loadClassLoader($class)
    {
        if ('Composer\Autoload\ClassLoader' === $class) {
            require __DIR__ . '/ClassLoader.php';
        }
    }

    /**
     * @return \Composer\Autoload\ClassLoader
     */
    public static function getLoader()
    {
        if (null !== self::$loader) {
            return self::$loader;
        }

        require __DIR__ . '/platform_check.php';

        spl_autoload_register(array('ComposerAutoloaderInitf35e8fd58eb377b73fdf83653dc88f05', 'loadClassLoader'), true, true);
        self::$loader = $loader = new \Composer\Autoload\ClassLoader(\dirname(__DIR__));
        spl_autoload_unregister(array('ComposerAutoloaderInitf35e8fd58eb377b73fdf83653dc88f05', 'loadClassLoader'));

        require __DIR__ . '/autoload_static.php';
        call_user_func(\Composer\Autoload\ComposerStaticInitf35e8fd58eb377b73fdf83653dc88f05::getInitializer($loader));

        $loader->register(true);

        return $loader;
    }
}
