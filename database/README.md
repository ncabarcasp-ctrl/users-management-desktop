# Base de datos

Motor: **MySQL 8.0** (instalado localmente). Base: `crud_usuarios`.

| Tabla | Entidad | Adaptador |
|---|---|---|
| `users` | User | `UserRepositoryMySQL` |
| `radios` | Radio (CEA Emisoras) | `RadioRepositoryMySQL` |

Ambos adaptadores usan **JDBC puro** (`PreparedStatement`); no se usa ningún ORM.

## Restaurar el backup

Desde la raíz del proyecto, en PowerShell:

```powershell
mysql -u root -p -e "source database/backup_crud_usuarios.sql"
```

El script crea la base si no existe y **recrea** las tablas `users` y `radios` con datos de ejemplo
(3 radios y el usuario administrador inicial). Si ya tenías datos en esas tablas, se pierden.

## Conexión

Copia tus datos en `src/main/resources/application.properties` (`db.host`, `db.port`, `db.name`,
`db.username`, `db.password`). **No subas tu contraseña real a GitHub.**
