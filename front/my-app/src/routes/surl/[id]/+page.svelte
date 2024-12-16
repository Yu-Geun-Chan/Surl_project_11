<script lang="ts">
	import { page } from '$app/stores';
	import type { components } from '$lib/backend/apiV1/schema';
	import rq from '$lib/rq/rq.svelte';
	let surl = $state<components['schemas']['SurlDto'] | null>(null);
	let errorMessage = $state<string | null>(null);
	async function getSurl() {
		const { data, error } = await rq.getClient().GET('/api/v1/surls/{id}', {
			params: {
				path: {
					id: parseInt($page.params.id)
				}
			}
		});
		if (data) {
			surl = data.data.item;
		} else if (error) {
			errorMessage = error.msg;
		}
	}
	$effect(() => {
		getSurl();
	});
</script>

<h1>{$page.params.id}번 SURL 페이지</h1>
{#if surl}
	<div>
		ID : {surl.id}
	</div>
	<div>
		생성날짜 : {surl.createDate}
	</div>
	<div>
		수정날짜 : {surl.modifyDate}
	</div>
	<div>
		URL : {surl.url}
	</div>
	<div>
		내용 : {surl.body}
	</div>
{:else if errorMessage}
	<div>{errorMessage}</div>
{/if}
