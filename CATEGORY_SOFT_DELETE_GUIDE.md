# 📚 Guía de Soft Delete y Gestión Avanzada de Categorías

## 🎯 Funcionalidades Implementadas

### 1. **Soft Delete** (Borrado Lógico)
### 2. **Validación de Categorías Activas**
### 3. **Filtrado por Estado** (Query Parameter)
### 4. **Toggle de Estado Rápido** (PATCH endpoint)
### 5. **Protección de Integridad de Datos**

---

## ✅ BENEFICIOS

### 🔒 **1. Integridad de Datos**
- **Antes**: Si borrabas una categoría, perdías todo el historial
- **Ahora**: Las categorías se marcan como inactivas, preservando el historial completo

### 📊 **2. Auditoría y Trazabilidad**
- Puedes saber qué categorías existieron en el pasado
- Mantiene referencias de productos que usaron categorías desactivadas
- Útil para reportes históricos y análisis

### 🛡️ **3. Prevención de Errores**
- No puedes desactivar una categoría si tiene productos activos
- No puedes crear productos con categorías inactivas
- Evita referencias rotas en la base de datos

### 🔄 **4. Reversibilidad**
- Puedes reactivar una categoría fácilmente con un toggle
- No necesitas recrear datos desde cero
- Menos propenso a errores humanos

---

## 🚀 EJEMPLOS DE USO

### **Ejemplo 1: Listar Solo Categorías Activas** ✅

**Caso de Uso**: El frontend necesita mostrar solo las categorías disponibles en un dropdown.

```bash
GET /api/v1/categories?active=true
```

**Respuesta**:
```json
[
  {
    "id": 1,
    "name": "Escolar y Oficina",
    "description": "...",
    "active": true,
    "createdAt": "2026-01-17T20:52:16",
    "updatedAt": "2026-01-17T20:52:16"
  },
  {
    "id": 2,
    "name": "Hogar y Limpieza",
    "description": "...",
    "active": true,
    "createdAt": "2026-01-17T21:06:40",
    "updatedAt": "2026-01-17T21:06:40"
  }
]
```

**💡 Beneficio**: El dropdown del frontend solo muestra opciones válidas automáticamente.

---

### **Ejemplo 2: Ver Categorías Inactivas** 📁

**Caso de Uso**: Admin quiere ver qué categorías fueron desactivadas para posible reactivación.

```bash
GET /api/v1/categories?active=false
```

**Respuesta**:
```json
[
  {
    "id": 5,
    "name": "Electrónica (Descontinuada)",
    "description": "...",
    "active": false,
    "createdAt": "2025-12-10T10:00:00",
    "updatedAt": "2026-01-15T14:30:00"
  }
]
```

**💡 Beneficio**: No pierdes el historial, puedes revisar y reactivar si es necesario.

---

### **Ejemplo 3: Intentar Desactivar Categoría con Productos** ❌

**Caso de Uso**: Intentas desactivar "Escolar y Oficina" pero tiene 15 productos activos.

```bash
DELETE /api/v1/categories/1
```

**Respuesta (Error 400)**:
```json
{
  "timestamp": "2026-01-22T12:30:00",
  "status": 400,
  "error": "Business Rule Violation",
  "message": "Cannot deactivate category: 15 active product(s) are still using it. Please reassign or deactivate products first.",
  "path": "/api/v1/categories/1"
}
```

**💡 Beneficio**: Evita dejar productos huérfanos o con referencias rotas.

---

### **Ejemplo 4: Desactivar Categoría Vacía** ✅

**Caso de Uso**: Desactivas "Temporada Navidad" después de la temporada (sin productos activos).

```bash
DELETE /api/v1/categories/8
```

**Respuesta**: `204 No Content`

**Verificación**:
```bash
GET /api/v1/categories/8
```

```json
{
  "id": 8,
  "name": "Temporada Navidad",
  "description": "...",
  "active": false,  // ✅ Marcada como inactiva
  "createdAt": "2025-11-01T08:00:00",
  "updatedAt": "2026-01-22T12:35:00"
}
```

**💡 Beneficio**: La categoría sigue existiendo en la BD pero no se muestra en las listas activas.

---

### **Ejemplo 5: Toggle Rápido de Estado** ⚡

**Caso de Uso**: Reactivar rápidamente "Temporada Navidad" para el próximo año.

```bash
PATCH /api/v1/categories/8/toggle
```

**Respuesta**:
```json
{
  "id": 8,
  "name": "Temporada Navidad",
  "description": "...",
  "active": true,  // ✅ Reactivada!
  "createdAt": "2025-11-01T08:00:00",
  "updatedAt": "2026-11-15T09:00:00"
}
```

**💡 Beneficio**: Un solo clic para activar/desactivar, no necesitas un formulario completo.

---

### **Ejemplo 6: Protección al Crear Productos** 🛡️

**Caso de Uso**: Intentas crear un producto con una categoría inactiva.

```bash
POST /api/v1/products
{
  "name": "Cuaderno Universitario",
  "categoryId": 5,  // ❌ Categoría inactiva
  "price": 15.99,
  "stock": 50
}
```

**Respuesta (Error 400)**:
```json
{
  "timestamp": "2026-01-22T13:00:00",
  "status": 400,
  "error": "Business Rule Violation",
  "message": "Cannot use inactive category: 'Electrónica (Descontinuada)'. Please select an active category.",
  "path": "/api/v1/products"
}
```

**💡 Beneficio**: Los productos siempre tienen categorías válidas y activas.

---

### **Ejemplo 7: Cambiar Categoría de Producto** 🔄

**Caso de Uso**: Moviendo un producto a otra categoría, validando que la nueva esté activa.

```bash
PUT /api/v1/products/42
{
  "name": "Mouse Inalámbrico",
  "categoryId": 1,  // ✅ Categoría activa
  "price": 25.99,
  "stock": 30
}
```

**Respuesta**: `200 OK` con el producto actualizado.

**Pero si intentas moverlo a una inactiva**:

```bash
PUT /api/v1/products/42
{
  "name": "Mouse Inalámbrico",
  "categoryId": 5,  // ❌ Categoría inactiva
  "price": 25.99,
  "stock": 30
}
```

**Respuesta (Error 400)**:
```json
{
  "message": "Cannot use inactive category: 'Electrónica (Descontinuada)'. Please select an active category."
}
```

**💡 Beneficio**: Los productos solo pueden estar en categorías activas.

---

## 🔍 FLUJOS DE TRABAJO COMUNES

### **Flujo 1: Descontinuar una Línea de Productos**

1. **Desactivar todos los productos** de la categoría:
   ```bash
   # Marcar cada producto como inactivo (si tienes endpoint de soft delete para products)
   DELETE /api/v1/products/101
   DELETE /api/v1/products/102
   # ... etc
   ```

2. **Desactivar la categoría**:
   ```bash
   DELETE /api/v1/categories/5
   # ✅ Ahora sí funciona porque no hay productos activos
   ```

3. **Resultado**: 
   - Historial completo preservado
   - Ya no aparece en el frontend
   - Datos disponibles para reportes

---

### **Flujo 2: Auditoría de Categorías**

```bash
# Ver todas las categorías (activas + inactivas)
GET /api/v1/categories

# Solo activas (para frontend)
GET /api/v1/categories?active=true

# Solo inactivas (para revisión de admin)
GET /api/v1/categories?active=false
```

---

### **Flujo 3: Temporadas o Promociones**

```bash
# Inicio de temporada: Crear categoría
POST /api/v1/categories
{
  "name": "Black Friday 2026",
  "description": "Ofertas especiales"
}

# Durante la temporada: Asignar productos
POST /api/v1/products
{
  "name": "Pack Oferta",
  "categoryId": 10,
  "price": 29.99,
  "stock": 100
}

# Fin de temporada: Desactivar categoría
DELETE /api/v1/categories/10

# Próximo año: Reactivar con un toggle
PATCH /api/v1/categories/10/toggle
```

---

## 📊 COMPARACIÓN: ANTES vs AHORA

| Escenario | ❌ Antes (Hard Delete) | ✅ Ahora (Soft Delete) |
|-----------|----------------------|----------------------|
| **Borrar categoría** | Se elimina de la BD | Se marca como `active=false` |
| **Productos huérfanos** | Quedan sin categoría (error) | Se previene la desactivación |
| **Historial** | Se pierde completamente | Se mantiene para auditoría |
| **Reactivar** | Imposible, hay que recrear | Toggle rápido |
| **Reportes históricos** | No disponibles | Completamente disponibles |
| **Integridad de datos** | Riesgo alto | Protegido |

---

## 🎨 INTEGRACIÓN CON EL FRONTEND

### **Dropdown de Categorías**
```typescript
// React/Angular/Vue example
useEffect(() => {
  // Solo cargar categorías activas
  fetch('/api/v1/categories?active=true')
    .then(res => res.json())
    .then(categories => setCategories(categories));
}, []);
```

### **Panel de Administración**
```typescript
// Mostrar todas las categorías con indicador visual
fetch('/api/v1/categories')
  .then(res => res.json())
  .then(categories => {
    // Aplicar estilos diferentes según active
    categories.map(cat => ({
      ...cat,
      className: cat.active ? 'badge-success' : 'badge-secondary'
    }));
  });
```

### **Toggle Button**
```typescript
const toggleCategory = async (id) => {
  try {
    const response = await fetch(`/api/v1/categories/${id}/toggle`, {
      method: 'PATCH'
    });
    const updated = await response.json();
    // Actualizar UI con el nuevo estado
    console.log(`Category is now ${updated.active ? 'ACTIVE' : 'INACTIVE'}`);
  } catch (error) {
    // Manejar error (ej: productos activos)
    alert(error.message);
  }
};
```

---

## 🧪 PRUEBAS RECOMENDADAS

### **Test 1: No se puede desactivar con productos activos**
```bash
# 1. Crear categoría
# 2. Crear producto en esa categoría
# 3. Intentar DELETE /categories/{id}
# 4. Debe fallar con 400
```

### **Test 2: Sí se puede desactivar si está vacía**
```bash
# 1. Crear categoría
# 2. NO crear productos
# 3. DELETE /categories/{id}
# 4. Debe responder 204
# 5. GET /categories/{id} debe mostrar active=false
```

### **Test 3: Toggle funciona correctamente**
```bash
# 1. PATCH /categories/{id}/toggle (activa → inactiva)
# 2. Verificar active=false
# 3. PATCH /categories/{id}/toggle (inactiva → activa)
# 4. Verificar active=true
```

### **Test 4: Filtrado funciona**
```bash
# 1. GET /categories?active=true debe mostrar solo activas
# 2. GET /categories?active=false debe mostrar solo inactivas
# 3. GET /categories debe mostrar todas
```

---

## 🔐 LOGS Y MONITOREO

Todos los métodos incluyen logs detallados:

```log
2026-01-22 12:30:15 INFO  CategoryServiceImpl - Creating new category: Black Friday 2026
2026-01-22 12:30:15 INFO  CategoryServiceImpl - Category created successfully with ID: 10

2026-01-22 13:45:30 INFO  CategoryServiceImpl - Attempting to delete (soft delete) category ID: 5
2026-01-22 13:45:30 WARN  CategoryServiceImpl - Cannot deactivate category ID 5: has 15 active products

2026-01-22 14:00:00 INFO  CategoryServiceImpl - Toggling active status for category ID: 8
2026-01-22 14:00:00 INFO  CategoryServiceImpl - Category ID 8 status changed to: ACTIVE

2026-01-22 15:30:00 INFO  ProductServiceImpl - Creating new product: Cuaderno A4
2026-01-22 15:30:00 DEBUG CategoryServiceImpl - Validating if category ID 1 is active
```

---

## 📝 RESUMEN DE ENDPOINTS

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/v1/categories` | Lista todas |
| `GET` | `/api/v1/categories?active=true` | Solo activas |
| `GET` | `/api/v1/categories?active=false` | Solo inactivas |
| `GET` | `/api/v1/categories/{id}` | Ver una categoría |
| `POST` | `/api/v1/categories` | Crear nueva |
| `PUT` | `/api/v1/categories/{id}` | Actualizar |
| `DELETE` | `/api/v1/categories/{id}` | Soft delete (marca inactive) |
| `PATCH` | `/api/v1/categories/{id}/toggle` | Toggle active/inactive |

---

## 🚀 PRÓXIMOS PASOS RECOMENDADOS

1. ✅ **Testing**: Crear tests unitarios y de integración
2. ✅ **Frontend**: Integrar los nuevos endpoints
3. ✅ **Documentación**: Actualizar Swagger/OpenAPI
4. ✅ **Migración**: Verificar datos existentes en la BD
5. ✅ **Soft Delete en Products**: Aplicar el mismo patrón a productos

---

## ❓ PREGUNTAS FRECUENTES

### ¿Qué pasa con los productos que ya tienen categorías inactivas?

Los productos existentes NO se ven afectados. La validación solo aplica a:
- Creación de nuevos productos
- Actualización de productos (cambio de categoría)

### ¿Puedo borrar físicamente una categoría si es necesario?

Sí, pero no a través del API. Solo un DBA con acceso directo a la BD puede hacer `DELETE` físico.

### ¿Cómo afecta esto a los reportes?

**Positivamente**. Ahora puedes generar reportes históricos incluyendo categorías descontinuadas.

### ¿El toggle puede desactivar si hay productos activos?

**NO**. El toggle también valida que no haya productos activos antes de desactivar.

---

## 📧 SOPORTE

Si tienes dudas o encuentras algún problema, revisa:
- Los logs de la aplicación
- El mensaje de error específico
- Esta documentación

---

**Versión**: 1.0.0  
**Última actualización**: 22 de enero de 2026  
**Autor**: Librería Coquito Development Team
