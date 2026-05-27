alter table public.productos enable row level security;
drop policy if exists "public_select_productos" on public.productos;
create policy "public_select_productos"
on public.productos
for select
to anon
using (true);

drop policy if exists "authenticated_select_productos" on public.productos;
create policy "authenticated_select_productos"
on public.productos
for select
to authenticated
using (true);

revoke insert, update, delete on public.productos from anon;
revoke insert, update, delete on public.productos from authenticated;

grant select on public.productos to anon;
grant select on public.productos to authenticated;

alter publication supabase_realtime add table public.productos;

