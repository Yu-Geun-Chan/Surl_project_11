<script lang="ts">
	import { goto } from '$app/navigation';

	import createClient from 'openapi-fetch';

	import type { paths } from '$lib/backend/apiV1/schema';

	type Client = ReturnType<typeof createClient<paths>>;
	const client: Client = createClient<paths>({
		baseUrl: import.meta.env.VITE_CORE_API_BASE_URL,
		credentials: 'include'
	});

	async function submitLoginForm(this: HTMLFormElement) {
		const form: HTMLFormElement = this;

		form.username.value = form.username.value.trim();
		if (form.username.value.length === 0) {
			alert('username 입력해');
			form.username.focus();
			return;
		}
		form.password.value = form.password.value.trim();
		if (form.password.value.length === 0) {
			alert('password 입력해');
			form.password.focus();
			return;
		}

		const { data, error } = await client.POST('/api/v1/members/login', {
			body: {
				username: form.username.value,
				password: form.password.value
			}
		});
		if (data) {
			data.msg && alert(data.msg);
			goto('/');
		} else if (error) {
			error.msg && alert(error.msg);
		}
	}
</script>

<form on:submit|preventDefault={submitLoginForm}>
	<div>
		<label>
			<span>username</span>
			<input type="text" name="username" placeholder="username 써" />
		</label>
	</div>
	<div>
		<label>
			<span>password</span>
			<input type="text" name="password" placeholder="password 써" />
		</label>
	</div>
	<div>
		<label>
			<span>로그인</span>
			<input type="submit" value="로그인" />
		</label>
	</div>
</form>
